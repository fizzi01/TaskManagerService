package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskFindingDTO {
    /**
     * The name of the task.
     */
    private String name;

    /**
     * The email of the user who is associated with the task.
     */
    private String emailUtente;

    /**
     * The maximum computing power that the task can use.
     * This is a measure of how much computational resources the task can consume.
     */
    private double maxComputingPower;

    /**
     * The maximum cuda power that the task can use.
     */
    private double maxCudaPower;

    /**
     * The minimum cuda power that the task requires.
     */
    private double minCudaPower;


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
     * The current status of the task.
     * If true, the task is currently running. If false, the task is not currently running.
     */
    private Boolean running;

    /**
     * The enabled status of the task.
     */
    private Boolean enabled;
}
