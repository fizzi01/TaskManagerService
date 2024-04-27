package it.unisalento.pasproject.taskmanagerservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskMessageAssignDTO {

    private String idTask;
    private List<String> assignedUsers;
}
