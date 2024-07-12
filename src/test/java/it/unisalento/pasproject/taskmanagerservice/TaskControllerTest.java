package it.unisalento.pasproject.taskmanagerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pasproject.taskmanagerservice.controller.TaskController;
import it.unisalento.pasproject.taskmanagerservice.domain.Task;

import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import it.unisalento.pasproject.taskmanagerservice.repositories.TaskRepository;
import it.unisalento.pasproject.taskmanagerservice.service.TaskMessageHandler;
import it.unisalento.pasproject.taskmanagerservice.service.TaskService;
import it.unisalento.pasproject.taskmanagerservice.service.UserCheckService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.data.mongodb.core.query.Query;


import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc()
@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean()
    private TaskService taskService;

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private TaskRepository taskRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private UserCheckService userCheckService;

    @MockBean
    private TaskMessageHandler taskMessageHandler;

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(TaskControllerTest.class);

    @InjectMocks
    private TaskController taskController;

    private Task task;
    private List<Task> tasks;
    private TaskDTO taskDTO;


    @BeforeEach
    void setUp() {
        given(taskService.getTaskDTO(any())).willCallRealMethod();
        given(taskService.getFromDTO(any())).willCallRealMethod();

        tasks = new ArrayList<>();

        task = new Task();
        task.setId("1");
        task.setName("Task 1");
        task.setEmailUtente("user@example.com");
        task.setEnabled(true);
        task.setRunning(true);
        task.setTaskDuration(10.0);
        task.setMaxEnergyConsumption(10.0);
        task.setMaxCudaPower(10.0);
        task.setMinCudaPower(10.0);
        task.setMinComputingPower(10.0);
        task.setMinEnergyConsumption(10.0);
        task.setMaxComputingPower(10.0);
        task.setMinWorkingTime(10.0);
        task.setDescription("description");
        task.setScript("script");

        Task task2 = new Task();
        task2.setId("2");
        task2.setName("Task 2");
        task2.setEmailUtente("user@example.com");
        task2.setEnabled(true);
        task2.setRunning(true);
        task2.setTaskDuration(10.0);
        task2.setMaxEnergyConsumption(10.0);
        task2.setMaxCudaPower(10.0);
        task2.setMinCudaPower(10.0);
        task2.setMinComputingPower(10.0);
        task2.setMinEnergyConsumption(10.0);
        task2.setMaxComputingPower(10.0);
        task2.setMinWorkingTime(10.0);
        task2.setDescription("description");
        task2.setScript("script");

        tasks.add(task);
        tasks.add(task2);

        taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setEmailUtente(task.getEmailUtente());
        taskDTO.setEnabled(task.getEnabled());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getByFiltersWithValidFiltersReturnsTaskList() throws Exception {
        // Given
        List<Task> expectedTaskList = new ArrayList<>();
        Task task1 = new Task();
        task1.setId("1");
        task1.setName("Task 1");
        task1.setEmailUtente("test@test.com");
        task1.setTaskDuration(10.0);
        task1.setMaxEnergyConsumption(10.0);
        task1.setMaxCudaPower(10.0);
        task1.setMinCudaPower(10.0);
        task1.setMinComputingPower(10.0);
        task1.setMinEnergyConsumption(10.0);
        task1.setMaxComputingPower(10.0);
        task1.setMinWorkingTime(10.0);
        task1.setDescription("description");
        task1.setScript("script");
        task1.setRunning(false);
        task1.setEnabled(true);

        Task task2 = new Task();
        task2.setId("2");
        task2.setName("Task 2");
        task2.setEmailUtente("test@test.com");
        task2.setTaskDuration(10.0);
        task2.setMaxEnergyConsumption(10.0);
        task2.setMaxCudaPower(10.0);
        task2.setMinCudaPower(10.0);
        task2.setMinComputingPower(10.0);
        task2.setMinEnergyConsumption(10.0);
        task2.setMaxComputingPower(10.0);
        task2.setMinWorkingTime(10.0);
        task2.setDescription("description");
        task2.setScript("script");
        task2.setRunning(false);
        task2.setEnabled(true);

        expectedTaskList.add(task1);
        expectedTaskList.add(task2);

        given(userCheckService.isCorrectUser(any(String.class))).willReturn(true);
        given(taskService.findTasks(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).willReturn(expectedTaskList);
        //given(taskService.getTaskDTO(any())).willCallRealMethod();

        // When & Then
        MvcResult r = mockMvc.perform(get("/api/tasks/find")
                        .param("email", "test@test.com")
                        .param("running", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks").isNotEmpty())
                .andExpect(jsonPath("$.tasks[0].id").exists()).andReturn();

        logger.info("getByFiltersWithValidFilters - Response: {}", r.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getByFiltersWithNoTasksFoundThrowsTaskNotFoundException() throws Exception {

        List<Task> expectedTaskList = new ArrayList<>();
        // Given
        given(userCheckService.isCorrectUser(any(String.class))).willReturn(true);
        given(mongoTemplate.find(any(Query.class), eq(Task.class), eq("tasks"))).willReturn(expectedTaskList);
        given(mongoTemplate.getCollectionName(Task.class)).willReturn("tasks");
        //given(taskService.findTasks(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).willReturn(expectedTaskList);

        // When & Then
        mockMvc.perform(get("/api/tasks/find")
                        .param("email", "test@test.com")
                        .param("running", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks").isEmpty());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "user@example.com")
    void createTaskWithValidDataReturnsCreatedTask() throws Exception {
        // Given
        Task expectedTask = new Task();

        expectedTask.setId("1");
        expectedTask.setName("New Task");
        expectedTask.setEmailUtente("user@example.com");
        expectedTask.setTaskDuration(10.0);
        expectedTask.setMaxEnergyConsumption(10.0);
        expectedTask.setMaxCudaPower(10.0);
        expectedTask.setMinCudaPower(10.0);
        expectedTask.setMinComputingPower(10.0);
        expectedTask.setMinEnergyConsumption(10.0);
        expectedTask.setMaxComputingPower(10.0);
        expectedTask.setMinWorkingTime(10.0);
        expectedTask.setDescription("description");
        expectedTask.setScript("script");
        expectedTask.setRunning(false);
        expectedTask.setEnabled(true);

        given(userCheckService.isCorrectUser(any(String.class))).willReturn(true);

        given(taskRepository.save(any(Task.class))).willReturn(expectedTask);

        // When & Then
        mockMvc.perform(post("/api/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Task\", \"emailUtente\":\"user@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(roles = "UTENTE")
    void createTaskWithInvalidUserThrowsTaskNotFoundException() throws Exception {
        // Given
        given(userCheckService.isCorrectUser(any(String.class))).willReturn(false);

        // When & Then
        mockMvc.perform(post("/api/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Invalid Task\", \"emailUtente\":\"invalid@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "UTENTE",username = "user@example.com")
    void updateTaskWithValidDataReturnsUpdatedTask() throws Exception {
        // Given
        Task expectedTask = new Task();
        expectedTask.setId("1");
        expectedTask.setName("Normal Task");
        expectedTask.setEmailUtente("user@example.com");
        expectedTask.setTaskDuration(10.0);
        expectedTask.setMaxEnergyConsumption(10.0);
        expectedTask.setMaxCudaPower(10.0);
        expectedTask.setMinCudaPower(10.0);
        expectedTask.setMinComputingPower(10.0);
        expectedTask.setMinEnergyConsumption(10.0);
        expectedTask.setMaxComputingPower(10.0);
        expectedTask.setMinWorkingTime(10.0);
        expectedTask.setDescription("description");
        expectedTask.setScript("script");
        expectedTask.setRunning(false);
        expectedTask.setEnabled(true);

        given(userCheckService.isCorrectUser(any(String.class))).willReturn(true);
        given(taskRepository.findById(any(String.class))).willReturn(java.util.Optional.of(expectedTask));

        expectedTask.setName("Updated Task");

        given(taskRepository.save(any(Task.class))).willReturn(expectedTask);

        // When & Then
        mockMvc.perform(put("/api/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "user@example.com")
    void enableTaskWithValidIdAndUserShouldReturnTaskDTO() throws Exception {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));
        when(userCheckService.isCorrectUser(anyString())).thenReturn(true);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/enable/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.enabled").value(true));
    }

    @Test
    @WithMockUser(roles = "UTENTE",username = "user@example.com")
    void disableTaskWithValidIdAndUserShouldReturnTaskDTO() throws Exception {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));
        when(userCheckService.isCorrectUser(anyString())).thenReturn(true);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/disable/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.enabled").value(false));
    }

    @Test
    @WithMockUser(roles = "UTENTE",username = "user@example.com")
    void runTaskWithValidIdAndUserShouldReturnTaskDTO() throws Exception {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));
        when(userCheckService.isCorrectUser(anyString())).thenReturn(true);

        task.setEndTime(null); // Task is not ended
        taskDTO.setRunning(true);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/run/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.running").value(true));
    }

    @Test
    @WithMockUser(roles = "UTENTE",username = "user@example.com")
    void stopTaskWithValidIdAndUserShouldReturnTaskDTO() throws Exception {

        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));
        when(userCheckService.isCorrectUser(anyString())).thenReturn(true);

        task.setEndTime(null); // Task is running
        taskDTO.setRunning(false);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/stop/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.running").value(false));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllTasksAsAdminReturnsAllTasks() throws Exception {

        given(taskRepository.findAll()).willReturn(tasks);

        for (Task t : tasks) {
            logger.info("Task: {}", t.getId());
        }

        mockMvc.perform(get("/api/tasks/find/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks", hasSize(2)))
                .andExpect(jsonPath("$.tasks[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$.tasks[1].id", Matchers.is("2")));
    }

    @Test
    @WithMockUser(roles = "UTENTE")
    void getAllTasksAsUserReturnsForbidden() throws Exception {
        mockMvc.perform(get("/api/tasks/find/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllTasksWhenNoTasksExistReturnsEmptyList() throws Exception {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/tasks/find/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks", hasSize(0)));
    }

}