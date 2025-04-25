package com.example.e_commerce_backend.dto;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 25/4/2025
 */
public class LoginResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
