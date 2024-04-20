package it.unisalento.pasproject.taskmanagerservice.repositories;

import it.unisalento.pasproject.taskmanagerservice.domain.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String> {
    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find.
     * @return The user with the given email.
     */
    Utente findByEmail(String email);

    /**
     * Finds a user by their company name.
     *
     * @param companyName The name of the company to find the user.
     * @return The user with the given company name.
     */
    Utente findByCompanyName(String companyName);
}
