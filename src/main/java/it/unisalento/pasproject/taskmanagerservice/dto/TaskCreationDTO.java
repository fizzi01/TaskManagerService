package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
public class TaskCreationDTO {

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The email of the user who is creating the task.
     */
    private String emailUtente;

    /**
     * The maximum computing power that the task can use.
     * This is a measure of how much computational resources the task can consume.
     */
    private double maxComputingPower;

    /**
     * The expected duration of the task, in seconds.
     */
    private double taskDuration;

    /**
     * The maximum energy consumption of the task.
     * This is a measure of how much energy the task can consume.
     */
    private double maxEnergyConsumption;

    /**
     * The minimum computing power that the task requires.
     * This is a measure of the minimum computational resources the task needs to run.
     */
    private double minComputingPower;

    /**
     * The minimum energy consumption of the task.
     * This is a measure of the minimum energy the task needs to run.
     */
    private double minEnergyConsumption;

    /**
     * The minimum working time of the task, in seconds.
     * This is a measure of the minimum time the task needs to complete its operation.
     */
    private double minWorkingTime;

    /**
     * A brief description of the task.
     */
    private String description;

    /**
     * The script of code associated with the task.
     * This should be a link to the script file and should be filled by another service.
     */
    private String script;

    /**
     * The current status of the task.
     * If true, the task is currently running. If false, the task is not currently running.
     */
    private Boolean running;

    /**
     * The enabled status of the task.
     * If true, the task is enabled. If false, the task is disabled.
     */
    private Boolean enabled;
}
