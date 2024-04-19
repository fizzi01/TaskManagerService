package it.unisalento.pasproject.taskmanagerservice.dto;

import it.unisalento.pasproject.taskmanagerservice.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListDTO {
    private List<Task> tasks;

    public TaskListDTO() {
        this.tasks = new ArrayList<>();
    }

}
