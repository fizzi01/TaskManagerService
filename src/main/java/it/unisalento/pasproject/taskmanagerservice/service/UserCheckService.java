package it.unisalento.pasproject.taskmanagerservice.service;


import it.unisalento.pasproject.taskmanagerservice.security.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Controlla che chi fa la richiesta sia un Utente e sia abilitato
@Service
public class UserCheckService {

    @Value("${service.role.name}")
    public static String ROLE;

    public UserDetailsDTO loadUserByUsername(String email) throws UsernameNotFoundException {

        //TODO: Chiamata MQTT a CQRS per ottenere i dettagli dell'utente
        final UserDetailsDTO user = new UserDetailsDTO(email, "USER", true);

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
