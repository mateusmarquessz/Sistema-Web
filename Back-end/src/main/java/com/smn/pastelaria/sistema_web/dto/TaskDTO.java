package com.smn.pastelaria.sistema_web.dto;
import com.smn.pastelaria.sistema_web.entity.Task;
import com.smn.pastelaria.sistema_web.enun.TaskStatus;

import java.time.LocalDate;

public class TaskDTO {

    private int id;
    private String message;
    private LocalDate dueDate;
    private TaskStatus status;
    private Long assignedUserId;  // ID do usuário ao qual a tarefa é atribuída
    private Long managerId;       // ID do gestor que cria a tarefa

    // Construtor padrão
    public TaskDTO() {}

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.status = task.getStatus();
    }


    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}
