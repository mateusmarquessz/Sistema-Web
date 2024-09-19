package com.smn.pastelaria.sistema_web.dto;


public class ResponseDTO {
    private String fullName;
    private String token;
    private String role;  // Adicione o campo role

    // Construtor com argumentos
    public ResponseDTO(String fullName, String token, String role) {
        this.fullName = fullName;
        this.token = token;
        this.role = role;
    }

    // Getters e Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}