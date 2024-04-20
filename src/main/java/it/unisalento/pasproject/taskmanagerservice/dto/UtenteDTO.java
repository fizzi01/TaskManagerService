package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteDTO {
    /**
     * The name of the user.
     */
    private String name;

    /**
     * The surname of the user.
     */
    private String surname;

    /**
     * The name of the company that the user belongs to.
     */
    private String companyName;

    /**
     * Information about the company that the user belongs to.
     */
    private String companyInfo;

    /**
     * The email of the user.
     */
    private String email;
}
