package com.smn.pastelaria.sistema_web.repository;

import com.smn.pastelaria.sistema_web.dto.UserWithoutPhotoDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Encontrar um usuário por e-mail
    Optional<User> findByEmail(String email);


    @Query("SELECT new com.smn.pastelaria.sistema_web.dto.UserWithoutPhotoDTO(u.id, u.email, u.password, u.fullName, u.role) FROM User u WHERE u.email = :email")
    Optional<UserWithoutPhotoDTO> findByEmailWithoutPhoto(@Param("email") String email);
}