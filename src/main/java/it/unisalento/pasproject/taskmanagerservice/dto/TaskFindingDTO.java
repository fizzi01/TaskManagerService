package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskFindingDTO {

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
     * Status of the task
     */
    private Boolean running;

}
