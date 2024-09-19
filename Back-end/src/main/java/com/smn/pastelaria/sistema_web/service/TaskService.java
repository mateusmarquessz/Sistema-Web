package com.smn.pastelaria.sistema_web.service;

import com.smn.pastelaria.sistema_web.dto.TaskDTO;
import com.smn.pastelaria.sistema_web.entity.Task;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.enun.TaskStatus;
import com.smn.pastelaria.sistema_web.exception.TaskNotFoundException;
import com.smn.pastelaria.sistema_web.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public TaskDTO createTask(TaskDTO taskDTO) {
        User assignedUser = userService.getUserById(taskDTO.getAssignedUserId());
        User manager = userService.getUserById(taskDTO.getManagerId());

        Task task = new Task();
        task.setMessage(taskDTO.getMessage());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(taskDTO.getStatus());
        task.setAssignedUser(assignedUser);
        task.setManager(manager);

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com ID: " + id));
        return convertToDTO(task);
    }

    public List<TaskDTO> getTasksByAssignedUser(Long userId) {
        User assignedUser = userService.getUserById(userId);
        List<Task> tasks = taskRepository.findByAssignedUser(assignedUser);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByManager(Long managerId) {
        User manager = userService.getUserById(managerId);
        List<Task> tasks = taskRepository.findByManager(manager);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setMessage(task.getMessage());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setAssignedUserId(task.getAssignedUser().getId());
        taskDTO.setManagerId(task.getManager().getId());
        return taskDTO;
    }

    public TaskDTO updateTaskStatus(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada"));

        if (task.getStatus().equals(TaskStatus.PENDENTE)) {
            task.setStatus(TaskStatus.CONCLUIDA);

            Task updatedTask = taskRepository.save(task);

            return new TaskDTO(updatedTask);
        } else {
            throw new IllegalStateException("A tarefa já foi concluída ou está em outro estado.");
        }
    }

    public TaskDTO statusTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada"));
        return convertToDTO(task);
    }


}