package it.unisalento.pasproject.taskmanagerservice.business;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class DomainDtoConversion {

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
