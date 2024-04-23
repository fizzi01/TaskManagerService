package it.unisalento.pasproject.taskmanagerservice.service;


import it.unisalento.pasproject.taskmanagerservice.business.io.exchanger.MessageExchangeStrategy;
import it.unisalento.pasproject.taskmanagerservice.business.io.exchanger.MessageExchanger;
import it.unisalento.pasproject.taskmanagerservice.security.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Controlla che chi fa la richiesta sia un Utente e sia abilitato
@Service
public class UserCheckService {

    @Value("${service.role.name}")
    public String ROLE;

    @Autowired
    private MessageExchanger messageExchanger;

    @Autowired
    @Qualifier("RabbitMQExchange")
    private MessageExchangeStrategy messageExchangeStrategy;


    public UserDetailsDTO loadUserByUsername(String email) throws UsernameNotFoundException {

        messageExchanger.setStrategy(messageExchangeStrategy);

        //Chiamata MQTT a CQRS per ottenere i dettagli dell'utente
        UserDetailsDTO user = messageExchanger.exchangeMessage(email, UserDetailsDTO.class);

        if(user == null) {
            throw new UsernameNotFoundException(email);
        }

        return user;
    }



    public Boolean roleCheck(String role) {
        return role.equals(ROLE);
    }

    public Boolean isEnable(Boolean enable) {
        return enable;
    }

}
