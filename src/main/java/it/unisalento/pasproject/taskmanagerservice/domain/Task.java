package it.unisalento.pasproject.taskmanagerservice.domain;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "task")
public class Task {

    /**
     * The unique identifier of the task.
     */
    @Id
    private String id;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The email of the user who created the task.
     */
    private String emailUtente;

    /**
     * The id of the user who created the task.
     */
    private String idUtente;

    /**
     * The maximum computing power that the task can use.
     */
    @NonNull
    private double maxComputingPower;

    /**
     * The expected duration of the task, in seconds.
     */
    @NonNull
    private double taskDuration;

    /**
     * The maximum energy consumption of the task.
     */
    private double maxEnergyConsumption;

    /**
     * The minimum computing power that the task requires.
     */
    private double minComputingPower;

    /**
     * The minimum energy consumption of the task.
     */
    private double minEnergyConsumption;

    /**
     * The minimum working time of the task, in seconds.
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

    /**
     * The list of users assigned to the task.
     */
    private List<String> assignedUsers;

    /**
     * Default constructor for the Task class.
     */
    public Task() {}

    /**
     * Constructor for the Task class with all fields.
     *
     * @param id The unique identifier of the task.
     * @param name The name of the task.
     * @param emailUtente The email of the user who created the task.
     * @param idUtente The id of the user who created the task.
     * @param maxComputingPower The maximum computing power that the task can use.
     * @param taskDuration The expected duration of the task, in seconds.
     * @param maxEnergyConsumption The maximum energy consumption of the task.
     * @param minComputingPower The minimum computing power that the task requires.
     * @param minEnergyConsumption The minimum energy consumption of the task.
     * @param minWorkingTime The minimum working time of the task, in seconds.
     * @param description A brief description of the task.
     * @param script The script of code associated with the task.
     * @param running The current status of the task.
     * @param assignedUsers The list of users assigned to the task.
     */
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