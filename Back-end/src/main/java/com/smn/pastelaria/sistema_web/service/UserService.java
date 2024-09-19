package com.smn.pastelaria.sistema_web.service;

import com.smn.pastelaria.sistema_web.dto.UserDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.exception.UserNotFoundException;
import com.smn.pastelaria.sistema_web.repository.UserRepository;
import com.smn.pastelaria.sistema_web.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.security.access.annotation.Secured;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) throws IOException {
        return registerUser(userDTO, null);
    }

    @Secured("ROLE_GESTOR") // Apenas usuários com a role GESTOR podem registrar outros usuários
    public User registerUser(UserDTO userDTO, MultipartFile file) throws IOException {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Usuário com e-mail já cadastrado");
        }

        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setPhone(userDTO.getPhone());
        user.setMobile(userDTO.getMobile());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        if (file != null && !file.isEmpty()) {
            user.setImage(file.getBytes());
            user.setImageUrl(ImageUtils.convertToBase64String(file.getBytes()));
        }

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
