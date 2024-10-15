package com.smn.pastelaria.sistema_web.service;

import com.smn.pastelaria.sistema_web.dto.UserDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.exception.ResourceNotFoundException;
import com.smn.pastelaria.sistema_web.exception.UserNotFoundException;
import com.smn.pastelaria.sistema_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerFirstManager(UserDTO userDTO) {
        // Converta o UserDTO para a entidade User
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setPhone(userDTO.getPhone());
        user.setMobile(userDTO.getMobile());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());

        // Encode a senha antes de salvar
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Defina a role GESTOR
        user.setRole(userDTO.getRole());

        // Salve o usuário no banco de dados
        userRepository.save(user);
    }


    //Registra Usuario
    @Secured("ROLE_GESTOR") // Apenas usuários com a role GESTOR podem registrar outros usuários
    public User registerUser(User user, MultipartFile file) throws IOException {
        if(file !=null && !file.isEmpty()){
            user.setImage(file.getBytes());
        }
        return userRepository.save(user);
    }

    //Pega dados do Usuario pelo Email
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    //Pega dados do Usuario pelo ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
    }

    //Pega Foto do Usuario
    public byte[] getUserPhotoById(Long id) throws ResourceNotFoundException {
        // Verifica se o usuário existe no banco de dados
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            // Obtém o usuário
            User user = userOptional.get();

            // Retorna a foto de perfil (que é um byte array)
            return user.getImage();
        } else {
            throw new ResourceNotFoundException("Usuário não encontrado com id: " + id);
        }
    }

    //Lista Usuario
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    //Deletar Usuario
    @Secured("ROLE_GESTOR")
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
