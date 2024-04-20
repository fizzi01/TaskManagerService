package it.unisalento.pasproject.taskmanagerservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "utente")
public class Utente {
    /**
     * The unique identifier of the user.
     */
    @Id
    private String id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The surname of the user.
     */
    private String surname;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The name of the company that the user belongs to.
     */
    private String companyName;

    /**
     * Information about the company that the user belongs to.
     */
    private String companyInfo;

    /**
     * Constructor for the Utente class with all fields.
     *
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param email The email of the user.
     * @param companyName The name of the company that the user belongs to.
     * @param companyInfo Information about the company that the user belongs to.
     */
    public Utente(String name, String surname, String email, String companyName, String companyInfo) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.companyName = companyName;
        this.companyInfo = companyInfo;
    }
}