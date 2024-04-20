package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
public class TaskCreationDTO {
    private String id;

    /**
     * Task name
     */
    private String name;

    private String emailUtente;

    /**
     * Task max computing power
     */
    private double maxComputingPower;

    /**
     * Task duration
     */
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

    /**
     * Status of the task
     */
    private Boolean running;
}
