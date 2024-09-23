package com.smn.pastelaria.sistema_web.controller;


import com.smn.pastelaria.sistema_web.dto.UserDTO;
import com.smn.pastelaria.sistema_web.entity.User;
import com.smn.pastelaria.sistema_web.enun.Role;
import com.smn.pastelaria.sistema_web.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import com.smn.pastelaria.sistema_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Base64;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Secured("ROLE_GESTOR")
    public ResponseEntity<User> createUser(@RequestParam("fullName") String fullName,
                                           @RequestParam("birthDate") LocalDate birthDate,
                                           @RequestParam("phone") String phone,
                                           @RequestParam("mobile") String mobile,
                                           @RequestParam("address") String address,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam("role") String role,
                                           @RequestParam(value = "image", required = false) MultipartFile file) { // image é opcional por enquanto
        try {
            User user = new User();
            user.setFullName(fullName);
            user.setBirthDate(birthDate);
            user.setPhone(phone);
            user.setMobile(mobile);
            user.setAddress(address);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.valueOf(role));

            // Imagem opicional por enquanto.
        /*
        if (file != null && !file.isEmpty()) {
            user.setImage(file.getBytes());
        }
        */

            User createUser = userService.registerUser(user, file); // Chame o serviço sem a imagem
            return new ResponseEntity<>(createUser, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Método para converter byte[] para String base64
    private String convertToBase64String(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserByEmail() {
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        if (user.getImage() != null) {
            String base64Image = convertToBase64String(user.getImage());
            String imageUrl = "data:image/png;base64," + base64Image;
            user.setImageUrl(imageUrl);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/photo")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable Long id) throws ResourceNotFoundException {
        byte[] photo = userService.getUserPhotoById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
    }

    @GetMapping("listar")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Secured("ROLE_GESTOR")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

