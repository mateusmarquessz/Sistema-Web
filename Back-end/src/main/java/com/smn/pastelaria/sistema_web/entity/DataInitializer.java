package com.smn.pastelaria.sistema_web.entity;

import com.smn.pastelaria.sistema_web.dto.UserDTO;
import com.smn.pastelaria.sistema_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.smn.pastelaria.sistema_web.enun.Role;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Autowired
    private UserService userService;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Verifica se o usuário padrão já existe
            if (userService.getUserByEmail("opotunidades@smn.com.br") == null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setFullName("Gestor Padrão");
                userDTO.setBirthDate(LocalDate.now());
                userDTO.setPhone("000000000");
                userDTO.setMobile("000000000");
                userDTO.setAddress("Endereço Padrão");
                userDTO.setEmail("opotunidades@smn.com.br");
                userDTO.setPassword("teste123");
                userDTO.setRole(Role.GESTOR);

                userService.registerFirstManager(userDTO);
            }
        };
    }
}

