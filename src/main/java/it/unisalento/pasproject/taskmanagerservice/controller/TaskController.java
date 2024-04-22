package it.unisalento.pasproject.taskmanagerservice.controller;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskCreationDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskFindingDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskListDTO;
import it.unisalento.pasproject.taskmanagerservice.exceptions.TaskNotFoundException;
import it.unisalento.pasproject.taskmanagerservice.repositories.TaskRepository;
import it.unisalento.pasproject.taskmanagerservice.service.RabbitMQJsonProducer;
import it.unisalento.pasproject.taskmanagerservice.service.RabbitMQProducer;
import it.unisalento.pasproject.taskmanagerservice.service.TaskFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static it.unisalento.pasproject.taskmanagerservice.business.DomainDtoConversion.getTaskDTO;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    /**
     * RabbitMQProducer instance for sending simple messages.
     */
    private RabbitMQProducer rabbitMQProducer;

    /**
     * RabbitMQJsonProducer instance for sending JSON messages.
     */
    private RabbitMQJsonProducer rabbitMQJsonProducer;

    /**
     * TaskRepository instance for accessing the task data.
     */
    @Autowired
    TaskRepository taskRepository;

    /**
     * TaskFindService instance for finding tasks based on various criteria.
     */
    @Autowired
    private TaskFindService taskFindService;

    /**
     * Constructor for the TaskController.
     *
     * @param rabbitMQProducer The RabbitMQProducer instance to use for sending simple messages.
     * @param rabbitMQJsonProducer The RabbitMQJsonProducer instance to use for sending JSON messages.
     */
    public TaskController(RabbitMQProducer rabbitMQProducer, RabbitMQJsonProducer rabbitMQJsonProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.rabbitMQJsonProducer = rabbitMQJsonProducer;
    }

    /**
     * Endpoint for sending a simple message to RabbitMQ.
     *
     * @param message The message to send.
     * @return A ResponseEntity indicating that the message was sent.
     */
    @GetMapping(value="/test")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent");
    }

    /**
     * Endpoint for sending a JSON message to RabbitMQ.
     *
     * @param taskDTO The TaskDTO to send as a JSON message.
     * @return A ResponseEntity indicating that the JSON message was sent.
     */
    @PostMapping(value="/test")
    public ResponseEntity<String> sendMessage(@RequestBody TaskDTO taskDTO) {
        rabbitMQJsonProducer.sendJsonMessage(taskDTO);
        return ResponseEntity.ok("Json message sent");
    }

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
    public TaskListDTO getByFilters(@RequestParam() String email,
                                    @RequestParam(required = false) Boolean running,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Double maxComputingPower,
                                    @RequestParam(required = false) Double minComputingPower,
                                    @RequestParam(required = false) Double maxEnergyConsumption,
                                    @RequestParam(required = false) Double minEnergyConsumption,
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
                minEnergyConsumption,
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
     * This method is used to find tasks based on various filters.
     *
     * @param taskToFind A TaskFindingDTO object containing the filters to be used in the search.
     * @return A TaskListDTO object containing a list of TaskDTO objects that match the provided filters.
     * @throws TaskNotFoundException If no tasks are found that match the provided filters.
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

    /**
     * This method is used to create a new task.
     *
     * @param newTask A TaskCreationDTO object containing the details of the task to be created.
     * @return A TaskDTO object containing the details of the created task.
     * @throws TaskNotFoundException If the task could not be created.
     */
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
