package it.unisalento.pasproject.taskmanagerservice.repositories;

import it.unisalento.pasproject.taskmanagerservice.domain.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String> {
    Utente findByEmail(String email);
    Utente findByCompanyName(String companyName);
}
