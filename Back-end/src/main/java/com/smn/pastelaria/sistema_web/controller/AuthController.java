package com.smn.pastelaria.sistema_web.controller;

import com.smn.pastelaria.sistema_web.config.TokenService;
import com.smn.pastelaria.sistema_web.dto.LoginRequestDTO;
import com.smn.pastelaria.sistema_web.dto.ResponseDTO;
import com.smn.pastelaria.sistema_web.dto.UserWithoutPhotoDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://sistema-web-khaki.vercel.app")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        try {
            // Busca o usuário sem a foto
            UserWithoutPhotoDTO userDTO = this.repository.findByEmailWithoutPhoto(body.email())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Verifica se a senha está correta
            if (passwordEncoder.matches(body.password(), userDTO.getPassword())) {
                // Converte o DTO de volta para User (somente os dados necessários)
                User user = new User();
                user.setEmail(userDTO.getEmail());
                user.setPassword(userDTO.getPassword());
                user.setFullName(userDTO.getFullName());
                user.setRole(userDTO.getRole());

                String token = this.tokenService.generateToken(user);

                return ResponseEntity.ok(new ResponseDTO(userDTO.getFullName(), token, userDTO.getRole().name()));
            }

            return ResponseEntity.status(401).body("Credenciais inválidas");

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
