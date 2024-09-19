package com.smn.pastelaria.sistema_web.repository;

import com.smn.pastelaria.sistema_web.entity.Task;
import com.smn.pastelaria.sistema_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Encontrar todas as tarefas atribuídas a um usuário específico
    List<Task> findByAssignedUser(User assignedUser);

    // Encontrar todas as tarefas criadas por um gestor específico
    List<Task> findByManager(User manager);
}