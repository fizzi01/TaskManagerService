package it.unisalento.pasproject.taskmanagerservice.business;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class DomainDtoConversion {

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
    public static TaskDTO getTaskDTO(Task task) {
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


}
