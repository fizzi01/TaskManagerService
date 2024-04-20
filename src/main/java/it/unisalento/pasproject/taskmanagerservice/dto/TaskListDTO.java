package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskListDTO {
    /**
     * The list of tasks.
     */
    private List<TaskDTO> tasks;

    /**
     * Constructor for the TaskListDTO class.
     * Initializes the tasks list as a new ArrayList.
     */
    public TaskListDTO() {
        this.tasks = new ArrayList<>();
    }
}
