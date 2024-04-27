package it.unisalento.pasproject.taskmanagerservice.controller;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskCreationDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskFindingDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskListDTO;
import it.unisalento.pasproject.taskmanagerservice.exceptions.TaskNotFoundException;
import it.unisalento.pasproject.taskmanagerservice.repositories.TaskRepository;
import it.unisalento.pasproject.taskmanagerservice.service.TaskMessageHandler;
import it.unisalento.pasproject.taskmanagerservice.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    /**
     * TaskRepository instance for accessing the task data.
     */
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMessageHandler taskMessageHandler;


    /**
     * This method is used to find tasks based on various filters.
     *
     * @param email The email of the user associated with the tasks.
     * @param running The running status of the tasks. If true, only tasks that are currently running will be returned. If false, only tasks that are not currently running will be returned. If null, tasks will not be filtered by running status.
     * @param name The name of the tasks. If provided, only tasks with this name will be returned.
     * @param maxComputingPower The maximum computing power of the tasks. If provided, only tasks with a computing power less than or equal to this value will be returned.
     * @param minComputingPower The minimum computing power of the tasks. If provided, only tasks with a computing power greater than or equal to this value will be returned.
     * @param maxEnergyConsumption The maximum energy consumption of the tasks. If provided, only tasks with an energy consumption less than or equal to this value will be returned.
     * @param minEnergyConsumption The minimum energy consumption of the tasks. If provided, only tasks with an energy consumption greater than or equal to this value will be returned.
     * @param taskDuration The duration of the tasks. If provided, only tasks with a duration equal to this value will be returned.
     * @return A TaskListDTO object containing a list of TaskDTO objects that match the provided filters.
     * @throws TaskNotFoundException If no tasks are found that match the provided filters.
     */
    @GetMapping(value="/find")
    @Secured({"UTENTE"})
    public TaskListDTO getByFilters(@RequestParam() String email,
                                    @RequestParam(required = false) Boolean running,
                                    @RequestParam(required = false) Boolean enabled,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Double maxComputingPower,
                                    @RequestParam(required = false) Double minComputingPower,
                                    @RequestParam(required = false) Double maxEnergyConsumption,
                                    @RequestParam(required = false) Double minEnergyConsumption,
                                    @RequestParam(required = false) Double taskDuration) throws TaskNotFoundException{

        TaskListDTO taskList = new TaskListDTO();
        List<TaskDTO> list = new ArrayList<>();
        taskList.setTasks(list);

        List<Task> tasks = taskService.findTasks(
                running,
                enabled,
                email,
                name,
                maxComputingPower,
                minComputingPower,
                maxEnergyConsumption,
                minEnergyConsumption,
                taskDuration
        );

        if(tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }

        for (Task task : tasks){
            TaskDTO taskDTO = taskService.getTaskDTO(task);

            list.add(taskDTO);
        }

        return taskList;
    }

    /**
     * This method is used to find tasks based on various filters.
     *
     * @param taskToFind A TaskFindingDTO object containing the filters to be used in the search.
     * @return A TaskListDTO object containing a list of TaskDTO objects that match the provided filters.
     * @throws TaskNotFoundException If no tasks are found that match the provided filters.
     */
    @PostMapping(value="/find", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"UTENTE"})
    public TaskListDTO getByFilters(@RequestBody TaskFindingDTO taskToFind) throws TaskNotFoundException{
        TaskListDTO taskList = new TaskListDTO();
        List<TaskDTO> list = new ArrayList<>();
        taskList.setTasks(list);

        List<Task> tasks = taskService.findTasks(
                taskToFind.getRunning(),
                taskToFind.getEnabled(),
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
            TaskDTO taskDTO = taskService.getTaskDTO(task);

            list.add(taskDTO);
        }

        return taskList;
    }

    /**
     * This method is used to create a new task.
     *
     * @param newTask A TaskCreationDTO object containing the details of the task to be created.
     * @return A TaskDTO object containing the details of the created task.
     * @throws TaskNotFoundException If the task could not be created.
     */
    @PostMapping(value="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"UTENTE"})
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
        task.setEnabled(true); // Task enabled (true) with link of task's repository link
        /*Vedere se va bene così lo script settato*/
        task.setScript(newTask.getScript());
        task.setRunning(newTask.getRunning());
        task.setAssignedUsers(null);

        task = taskRepository.save(task);

        // Send message to other services
        taskMessageHandler.sendNewTaskMessage(taskService.getTaskMessageDTO(task));

        return taskService.getTaskDTO(task);
    }

    /**
     * This method is used to update a task.
     *
     * @param taskToUpdate A TaskDTO object containing the details of the task to be updated.
     * @return A TaskDTO object containing the details of the updated task.
     * @throws TaskNotFoundException If the task could not be updated.
     */
    @PutMapping(value="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"UTENTE"})
    public TaskDTO updateTask(@RequestBody TaskDTO taskToUpdate) throws TaskNotFoundException{

        Optional<Task> task = taskRepository.findById(taskToUpdate.getId());

        if(task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task retTask = task.get();

        // Utilizza Optional per verificare se un valore è presente o meno
        Optional.of(taskToUpdate.getName()).ifPresent(retTask::setName);
        Optional.of(taskToUpdate.getEmailUtente()).ifPresent(retTask::setEmailUtente);
        Optional.ofNullable(taskToUpdate.getMaxComputingPower()).ifPresent(retTask::setMaxComputingPower);
        Optional.ofNullable(taskToUpdate.getTaskDuration()).ifPresent(retTask::setTaskDuration);
        Optional.ofNullable(taskToUpdate.getMaxEnergyConsumption()).ifPresent(retTask::setMaxEnergyConsumption);
        Optional.ofNullable(taskToUpdate.getMinComputingPower()).ifPresent(retTask::setMinComputingPower);
        Optional.ofNullable(taskToUpdate.getMinEnergyConsumption()).ifPresent(retTask::setMinEnergyConsumption);
        Optional.ofNullable(taskToUpdate.getMinWorkingTime()).ifPresent(retTask::setMinWorkingTime);
        Optional.ofNullable(taskToUpdate.getDescription()).ifPresent(retTask::setDescription);
        Optional.ofNullable(taskToUpdate.getScript()).ifPresent(retTask::setScript);
        Optional.ofNullable(taskToUpdate.getRunning()).ifPresent(retTask::setRunning);
        Optional.ofNullable(taskToUpdate.getAssignedUsers()).ifPresent(retTask::setAssignedUsers);

        retTask = taskRepository.save(retTask);

        //Send Messages to other services
        taskMessageHandler.sendUpdateTaskMessage(taskService.getTaskMessageDTO(retTask));

        return taskService.getTaskDTO(retTask);
    }

    @PutMapping(value="/enable/{id}")
    @Secured({"UTENTE"})
    public TaskDTO enableTask(@PathVariable String id) throws TaskNotFoundException{

        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task retTask = task.get();
        retTask.setEnabled(true);
        retTask = taskRepository.save(retTask);

        //Send Messages to other services
        taskMessageHandler.sendUpdateTaskMessage(taskService.getTaskMessageDTO(retTask));

        return taskService.getTaskDTO(retTask);
    }

    @PutMapping(value="/disable/{id}")
    @Secured({"UTENTE"})
    public TaskDTO disableTask(@PathVariable String id) throws TaskNotFoundException{

        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task retTask = task.get();
        retTask.setEnabled(false);
        retTask = taskRepository.save(retTask);

        //Send Messages to other services
        taskMessageHandler.sendUpdateTaskMessage(taskService.getTaskMessageDTO(retTask));

        return taskService.getTaskDTO(retTask);
    }

    @PutMapping(value="/run/{id}")
    @Secured({"UTENTE"})
    public TaskDTO runTask(@PathVariable String id) throws TaskNotFoundException{

        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task retTask = task.get();
        retTask.setRunning(true);
        retTask = taskRepository.save(retTask);

        //Send Messages to other services
        taskMessageHandler.sendUpdateTaskMessage(taskService.getTaskMessageDTO(retTask));

        return taskService.getTaskDTO(retTask);
    }

    @PutMapping(value="/stop/{id}")
    @Secured({"UTENTE"})
    public TaskDTO stopTask(@PathVariable String id) throws TaskNotFoundException{

        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task retTask = task.get();
        retTask.setRunning(false);
        retTask = taskRepository.save(retTask);

        //Send Messages to other services
        taskMessageHandler.sendUpdateTaskMessage(taskService.getTaskMessageDTO(retTask));

        return taskService.getTaskDTO(retTask);
    }

    @GetMapping(value="/find/all")
    @Secured({"UTENTE"})
    public TaskListDTO getAllTasks() {
        TaskListDTO taskList = new TaskListDTO();
        List<TaskDTO> list = new ArrayList<>();
        taskList.setTasks(list);

        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks){
            TaskDTO taskDTO = taskService.getTaskDTO(task);

            list.add(taskDTO);
        }

        return taskList;
    }

}
