package com.smn.pastelaria.sistema_web.dto;
import com.smn.pastelaria.sistema_web.enun.Role;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class UserDTO {

    private long id;
    private String fullName;
    private LocalDate birthDate;
    private String phone;
    private String mobile;
    private String address;
    private String email;
    private String password;
    private Role role;
    private MultipartFile file;
    // Getters e Setters



    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
