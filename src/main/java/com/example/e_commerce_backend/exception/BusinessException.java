package com.example.e_commerce_backend.exception;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 25/4/2025
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
