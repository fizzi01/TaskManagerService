package it.unisalento.pasproject.taskmanagerservice.service;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Converts a Task domain object into a TaskDTO object.
     * <p>
     * This method is used to convert a Task domain object, which is used in the business logic layer,
     * into a TaskDTO object, which is used in the presentation layer. This conversion is necessary
     * because the presentation layer should not be exposed to the domain objects directly.
     *
     * @param task The Task domain object to be converted.
     * @return A TaskDTO object that contains the same data as the input Task object.
     */
    public TaskDTO getTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setEmailUtente(task.getEmailUtente());
        taskDTO.setMaxComputingPower(task.getMaxComputingPower());
        taskDTO.setTaskDuration(task.getTaskDuration());
        taskDTO.setMaxEnergyConsumption(task.getMaxEnergyConsumption());
        taskDTO.setMinComputingPower(task.getMinComputingPower());
        taskDTO.setMinEnergyConsumption(task.getMinEnergyConsumption());
        taskDTO.setMinWorkingTime(task.getMinWorkingTime());
        taskDTO.setScript(task.getScript());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setRunning(task.getRunning());
        taskDTO.setAssignedUsers(task.getAssignedUsers());
        return taskDTO;
    }


    /**
     * Finds tasks based on various criteria.
     *
     * @param running The running status of the tasks to find.
     * @param email The email of the user associated with the tasks to find.
     * @param name The name of the tasks to find.
     * @param maxComputingPower The maximum computing power of the tasks to find.
     * @param minComputingPower The minimum computing power of the tasks to find.
     * @param maxEnergyConsumption The maximum energy consumption of the tasks to find.
     * @param minEnergyConsumption The minimum energy consumption of the tasks to find.
     * @param taskDuration The duration of the tasks to find.
     * @return A list of tasks that match the given criteria.
     */
    public List<Task> findTasks(Boolean running, Boolean enabled , String email, String name, Double maxComputingPower, Double minComputingPower, Double maxEnergyConsumption, Double minEnergyConsumption, Double taskDuration) {
        Query query = new Query();

        // Add conditions based on parameters provided
        if (running != null) {
            query.addCriteria(Criteria.where("running").is(running));
        }
        if (enabled != null) {
            query.addCriteria(Criteria.where("enabled").is(enabled));
        } else { // If enabled is not provided, only return enabled tasks
            query.addCriteria(Criteria.where("enabled").is(true));
        }
        if (email != null) {
            query.addCriteria(Criteria.where("emailUtente").is(email));
        }
        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (maxComputingPower != null) {
            query.addCriteria(Criteria.where("maxComputingPower").lte(maxComputingPower));
        }
        if (minComputingPower != null) {
            query.addCriteria(Criteria.where("minComputingPower").gte(minComputingPower));
        }
        if (maxEnergyConsumption != null) {
            query.addCriteria(Criteria.where("maxEnergyConsumption").lte(maxEnergyConsumption));
        }
        if (minEnergyConsumption != null) {
            query.addCriteria(Criteria.where("minEnergyConsumption").gte(minEnergyConsumption));
        }
        if (taskDuration != null) {
            query.addCriteria(Criteria.where("taskDuration").gte(taskDuration));
        }

        return mongoTemplate.find(query, Task.class);
    }
}