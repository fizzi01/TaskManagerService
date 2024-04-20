package it.unisalento.pasproject.taskmanagerservice.controller;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskCreationDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskFindingDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskListDTO;
import it.unisalento.pasproject.taskmanagerservice.exceptions.TaskNotFoundException;
import it.unisalento.pasproject.taskmanagerservice.repositories.TaskRepository;
import it.unisalento.pasproject.taskmanagerservice.service.TaskFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static it.unisalento.pasproject.taskmanagerservice.business.DomainDtoConversion.getTaskDTO;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private TaskFindService taskFindService;

    /**
     *
     * @param email
     * @param running
     * @param name
     * @param maxComputingPower
     * @param minComputingPower
     * @param maxEnergyConsumption
     * @param minEnergy
     * @param taskDuration
     * @return
     * @throws TaskNotFoundException
     */
    @GetMapping(value="/find")
    public TaskListDTO getByFilters(@RequestParam() String email,
                                    @RequestParam(required = false) Boolean running,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Double maxComputingPower,
                                    @RequestParam(required = false) Double minComputingPower,
                                    @RequestParam(required = false) Double maxEnergyConsumption,
                                    @RequestParam(required = false) Double minEnergy,
                                    @RequestParam(required = false) Double taskDuration) throws TaskNotFoundException{

        TaskListDTO taskList = new TaskListDTO();
        List<TaskDTO> list = new ArrayList<>();
        taskList.setTasks(list);

        List<Task> tasks = taskFindService.findTasks(
                running,
                email,
                name,
                maxComputingPower,
                minComputingPower,
                maxEnergyConsumption,
                minEnergy,
                taskDuration
        );


        if(tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }

        for (Task task : tasks){
            TaskDTO taskDTO = getTaskDTO(task);

            list.add(taskDTO);
        }

        return taskList;
    }

    /**
     *
     * @param taskToFind
     * @return
     * @throws TaskNotFoundException
     */
    @PostMapping(value="/find", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskListDTO getByFilters(@RequestBody TaskFindingDTO taskToFind) throws TaskNotFoundException{
        TaskListDTO taskList = new TaskListDTO();
        List<TaskDTO> list = new ArrayList<>();
        taskList.setTasks(list);

        List<Task> tasks = taskFindService.findTasks(
                taskToFind.getRunning(),
                taskToFind.getEmailUtente(),
                taskToFind.getName(),
                taskToFind.getMaxComputingPower(),
                taskToFind.getMinComputingPower(),
                taskToFind.getMaxEnergyConsumption(),
                taskToFind.getMinEnergyConsumption(),
                taskToFind.getTaskDuration()
        );


        if(tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }

        for (Task task : tasks){
            TaskDTO taskDTO = getTaskDTO(task);

            list.add(taskDTO);
        }

        return taskList;
    }

    @PostMapping(value="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO createTask(@RequestBody TaskCreationDTO newTask) throws TaskNotFoundException{

        Task task = new Task();
        task.setName(newTask.getName());
        task.setEmailUtente(newTask.getEmailUtente());
        task.setMaxComputingPower(newTask.getMaxComputingPower());
        task.setTaskDuration(task.getTaskDuration());
        task.setMaxEnergyConsumption(newTask.getMaxEnergyConsumption());
        task.setMinComputingPower(newTask.getMinComputingPower());
        task.setMinEnergyConsumption(newTask.getMinEnergyConsumption());
        task.setMinWorkingTime(newTask.getMinWorkingTime());
        task.setDescription(newTask.getDescription());
        task.setTaskDuration(newTask.getTaskDuration());
        task.setScript(null);
        task.setRunning(newTask.getRunning());
        task.setAssignedUsers(null);

        task = taskRepository.save(task);

        return getTaskDTO(task);
    }

}
