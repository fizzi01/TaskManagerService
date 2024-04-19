package it.unisalento.pasproject.taskmanagerservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Utente {
    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String companyName;
    private String companyInfo;

    public Utente(String name, String surname, String email, String companyName, String companyInfo) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.companyName = companyName;
        this.companyInfo = companyInfo;
    }
}