package com.smn.pastelaria.sistema_web.controller;

import com.smn.pastelaria.sistema_web.config.TokenService;
import com.smn.pastelaria.sistema_web.dto.LoginRequestDTO;
import com.smn.pastelaria.sistema_web.dto.ResponseDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
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
            // Verifica se o usuário existe no banco de dados
            User user = this.repository.findByEmail(body.email())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Verifica se a senha está correta
            if (passwordEncoder.matches(body.password(), user.getPassword())) {
                String token = this.tokenService.generateToken(user);

                // Retorna o token e o nome completo do usuário
                return ResponseEntity.ok(new ResponseDTO(user.getFullName(), token, user.getRole().name()));
            }

            // Caso a senha esteja incorreta
            return ResponseEntity.status(401).body("Senha inválida");

        } catch (RuntimeException e) {
            // Tratamento de exceção se o usuário não for encontrado
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
