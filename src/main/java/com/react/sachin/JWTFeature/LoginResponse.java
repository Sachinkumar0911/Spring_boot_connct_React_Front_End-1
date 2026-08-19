package com.react.sachin.JWTFeature;

public class LoginResponse {
   private String message;
    private String token;
    private String username;
    private String name;
    private String role;
    
     public LoginResponse() {
    }
    public LoginResponse(
            String message,
            String token,
            String username,
            String name,
            String role) {

        this.message = message;
        this.token = token;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
