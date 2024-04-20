package it.unisalento.pasproject.taskmanagerservice.domain;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
/*It represents a Task submitted by a Utente to be filled with Membri that share their computing power*/
public class Task {
    /**
     * Task id
     */
    @Id
    private String id;

    /**
     * Task name
     */
    private String name;

    private String emailUtente;
    private String idUtente;

    /**
     * Task max computing power
     */
    @NonNull
    private double maxComputingPower;

    /**
     * Task duration
     */
    @NonNull
    private double taskDuration;

    /**
     * Task maximum computing power
     */
    private double maxEnergyConsumption;

    /**
     * Task minimum computing power
     */
    private double minComputingPower;

    /**
     * Task minimum energy
     */
    private double minEnergyConsumption;

    /**
     * Task mimimum working time
     */
    private double minWorkingTime;

    /**
     *  Task description
     */
    private String description;

    /**
     * Task script of code associated
     */
    private String script; // Link to the script file -> Should be filled by another service

    private Boolean running;


    /**
     * TODO
     */
    private List<String> assignedUsers;

    public Task() {}

    public Task(String id, String name, String emailUtente, String idUtente, double maxComputingPower, double taskDuration, double maxEnergyConsumption, double minComputingPower, double minEnergyConsumption, double minWorkingTime, String description, String script, Boolean running, List<String> assignedUsers) {
        this.id = id;
        this.name = name;
        this.emailUtente = emailUtente;
        this.idUtente = idUtente;
        this.maxComputingPower = maxComputingPower;
        this.taskDuration = taskDuration;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.minComputingPower = minComputingPower;
        this.minEnergyConsumption = minEnergyConsumption;
        this.minWorkingTime = minWorkingTime;
        this.description = description;
        this.script = script;
        this.running = running;
        this.assignedUsers = assignedUsers;
    }
}