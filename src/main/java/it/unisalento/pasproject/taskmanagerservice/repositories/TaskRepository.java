package it.unisalento.pasproject.taskmanagerservice.repositories;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.domain.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    /**
     *
     * @param running
     * @return
     */
    List<Task> findByRunning(Boolean running);

    /**
     *
     * @param creator
     * @return
     */
    List<Task> findByUtente(Utente creator);

    /**
     *
     * @param name
     * @param creator
     * @return
     */
    Task findByNameAndUtente(String name, Utente creator);

    /**
     *
     * @param maxComputingPower
     * @return
     */
    List<Task> findByMaxComputingPowerGreaterThanEqual(double maxComputingPower);

    /**
     *
     * @param maxComputingPower
     * @return
     */
    List<Task> findByMaxComputingPowerLessThanEqual(double maxComputingPower);

    /**
     *
     * @param taskDuration
     * @return
     */
    List<Task> findByTaskDurationGreaterThanEqual(double taskDuration);

    /**
     *
     * @param taskDuration
     * @return
     */
    List<Task> findByTaskDurationLessThanEqual(double taskDuration);

    /**
     *
     * @param maxEnergyConsumption
     * @return
     */
    List<Task> findByMaxEnergyConsumptionGreaterThanEqual(double maxEnergyConsumption);

    /**
     *
     * @param maxEnergyConsumption
     * @return
     */
    List<Task> findByMaxEnergyConsumptionLessThanEqual(double maxEnergyConsumption);

    /**
     *
     * @param minComputingPower
     * @return
     */
    List<Task> findByMinComputingPowerGreaterThanEqual(double minComputingPower);

    /**
     *
     * @param minComputingPower
     * @return
     */
    List<Task> findByMinComputingPowerLessThanEqual(double minComputingPower);

    /**
     *
     * @param minEnergy
     * @return
     */
    List<Task> findByMinEnergyGreaterThanEqual(double minEnergy);

    /**
     * 
     * @param minEnergy
     * @return
     */
    List<Task> findByMinEnergyLessThanEqual(double minEnergy);

}
