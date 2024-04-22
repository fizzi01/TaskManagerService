package it.unisalento.pasproject.taskmanagerservice.repositories;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    /**
     * Finds tasks by their running status.
     *
     * @param running The running status of the tasks to find.
     * @return A list of tasks with the given running status.
     */
    List<Task> findByRunning(Boolean running);

    /**
     * Finds tasks by their running status and the email of the user they are associated with.
     *
     * @param running The running status of the tasks to find.
     * @param email The email of the user the tasks are associated with.
     * @return A list of tasks with the given running status and user email.
     */
    List<Task> findByRunningAndEmailUtente(Boolean running, String email);

    /**
     * Finds tasks by their running status and the email of the user they are associated with.
     *
     * @param enabled The enabled status of the tasks to find.
     * @param email The email of the user the tasks are associated with.
     * @return A list of tasks with the given enabled status and user email.
     */
    List<Task> findByRunningAndEmailUtenteAndEnabled(Boolean enabled, String email, Boolean running);

    /**
     * Finds tasks by the email of the user they are associated with.
     *
     * @param email The email of the user the tasks are associated with.
     * @return A list of tasks associated with the given user email.
     */
    List<Task> findByEmailUtente(String email);

    /**
     * Finds a task by its name and the email of the user it is associated with.
     *
     * @param name The name of the task to find.
     * @param email The email of the user the task is associated with.
     * @return The task with the given name and user email.
     */
    Task findByNameAndEmailUtente(String name, String email);

    /**
     * Finds tasks with a maximum computing power greater than or equal to the given value.
     *
     * @param maxComputingPower The maximum computing power value to compare with.
     * @return A list of tasks with a maximum computing power greater than or equal to the given value.
     */
    List<Task> findByMaxComputingPowerGreaterThanEqual(double maxComputingPower);

    /**
     * Finds tasks with a maximum computing power less than or equal to the given value.
     *
     * @param maxComputingPower The maximum computing power value to compare with.
     * @return A list of tasks with a maximum computing power less than or equal to the given value.
     */
    List<Task> findByMaxComputingPowerLessThanEqual(double maxComputingPower);

    /**
     * Finds tasks with a duration greater than or equal to the given value.
     *
     * @param taskDuration The task duration value to compare with.
     * @return A list of tasks with a duration greater than or equal to the given value.
     */
    List<Task> findByTaskDurationGreaterThanEqual(double taskDuration);

    /**
     * Finds tasks with a duration less than or equal to the given value.
     *
     * @param taskDuration The task duration value to compare with.
     * @return A list of tasks with a duration less than or equal to the given value.
     */
    List<Task> findByTaskDurationLessThanEqual(double taskDuration);

    /**
     * Finds tasks with a maximum energy consumption greater than or equal to the given value.
     *
     * @param maxEnergyConsumption The maximum energy consumption value to compare with.
     * @return A list of tasks with a maximum energy consumption greater than or equal to the given value.
     */
    List<Task> findByMaxEnergyConsumptionGreaterThanEqual(double maxEnergyConsumption);

    /**
     * Finds tasks with a maximum energy consumption less than or equal to the given value.
     *
     * @param maxEnergyConsumption The maximum energy consumption value to compare with.
     * @return A list of tasks with a maximum energy consumption less than or equal to the given value.
     */
    List<Task> findByMaxEnergyConsumptionLessThanEqual(double maxEnergyConsumption);

    /**
     * Finds tasks with a minimum computing power greater than or equal to the given value.
     *
     * @param minComputingPower The minimum computing power value to compare with.
     * @return A list of tasks with a minimum computing power greater than or equal to the given value.
     */
    List<Task> findByMinComputingPowerGreaterThanEqual(double minComputingPower);

    /**
     * Finds tasks with a minimum computing power less than or equal to the given value.
     *
     * @param minComputingPower The minimum computing power value to compare with.
     * @return A list of tasks with a minimum computing power less than or equal to the given value.
     */
    List<Task> findByMinComputingPowerLessThanEqual(double minComputingPower);

    /**
     * Finds tasks with a minimum energy consumption greater than or equal to the given value.
     *
     * @param minEnergy The minimum energy consumption value to compare with.
     * @return A list of tasks with a minimum energy consumption greater than or equal to the given value.
     */
    List<Task> findByMinEnergyConsumptionGreaterThanEqual(double minEnergy);

    /**
     * Finds tasks with a minimum energy consumption less than or equal to the given value.
     *
     * @param minEnergy The minimum energy consumption value to compare with.
     * @return A list of tasks with a minimum energy consumption less than or equal to the given value.
     */
    List<Task> findByMinEnergyConsumptionLessThanEqual(double minEnergy);
}