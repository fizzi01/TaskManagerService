package it.unisalento.pasproject.taskmanagerservice.service;


import it.unisalento.pasproject.taskmanagerservice.business.io.exchanger.MessageExchangeStrategy;
import it.unisalento.pasproject.taskmanagerservice.business.io.exchanger.MessageExchanger;
import it.unisalento.pasproject.taskmanagerservice.dto.UserDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static it.unisalento.pasproject.taskmanagerservice.security.SecurityConstants.ROLE_ADMIN;

// Controlla che chi fa la richiesta sia un Utente e sia abilitato
@Service
public class UserCheckService {


    private final MessageExchanger messageExchanger;

    @Value("${rabbitmq.exchange.security.name}")
    private String securityExchange;

    @Value("${rabbitmq.routing.security.key}")
    private String securityRequestRoutingKey;

    @Autowired
    public UserCheckService(MessageExchanger messageExchanger, @Qualifier("RabbitMQExchange") MessageExchangeStrategy messageExchangeStrategy) {
        this.messageExchanger = messageExchanger;
        this.messageExchanger.setStrategy(messageExchangeStrategy);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCheckService.class);


    public UserDetailsDTO loadUserByUsername(String email) throws UsernameNotFoundException {

        //Chiamata MQTT a CQRS per ottenere i dettagli dell'utente
        UserDetailsDTO user = null;

        try {
            user = messageExchanger.exchangeMessage(email,securityRequestRoutingKey,securityExchange,UserDetailsDTO.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }


    public Boolean isEnable(Boolean enable) {
        return enable;
    }

    /**
     * Check if the current user is the user with the given email
     * @param email the email of the user to check
     * @return true if the current user is the user with the given email, false otherwise
     */
    public Boolean isCorrectUser(String email){
        return email.equals(( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    /**
     * Get the email of the current user
     * @return the email of the current user
     */
    public String getCurrentUserEmail(){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     * Check if the current user is an administrator
     * @return true if the current user is an administrator, false otherwise
     */
    public Boolean isAdministrator(){
        String currentRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return currentRole.equalsIgnoreCase(ROLE_ADMIN);
    }
}
