package com.smn.pastelaria.sistema_web.controller;

import com.smn.pastelaria.sistema_web.dto.TaskDTO;
import com.smn.pastelaria.sistema_web.dto.UserDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.enun.TaskStatus;
import com.smn.pastelaria.sistema_web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('GESTOR')")
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = (User) principal;
        taskDTO.setManagerId(loggedUser.getId());
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long id) {
        TaskDTO updatedTask = taskService.updateTaskStatus(id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByAssignedUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByAssignedUser(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<TaskDTO>> getTasksByManager(@PathVariable Long managerId) {
        List<TaskDTO> tasks = taskService.getTasksByManager(managerId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO> getTaskStatus(@PathVariable Long taskId) {
        TaskDTO taskDTO = taskService.statusTask(taskId);
        return ResponseEntity.ok(taskDTO);
    }
}
