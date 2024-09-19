package com.smn.pastelaria.sistema_web.repository;

import com.smn.pastelaria.sistema_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Encontrar um usuário por e-mail
    Optional<User> findByEmail(String email);

    // Verificar se um usuário com determinado e-mail existe
    boolean existsByEmail(String email);
}