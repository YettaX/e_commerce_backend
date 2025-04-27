package com.example.e_commerce_backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 26/4/2025
 */
public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static void info(Class<?> clazz, String message) {
        Logger log = LoggerFactory.getLogger(clazz);
        log.info(message);
    }

    public static void warn(Class<?> clazz, String message) {
        Logger log = LoggerFactory.getLogger(clazz);
        log.warn(message);
    }

    public static void error(Class<?> clazz, String message, Throwable e) {
        Logger log = LoggerFactory.getLogger(clazz);
        log.error(message, e);
    }

    public static void debug(Class<?> clazz, String message) {
        Logger log = LoggerFactory.getLogger(clazz);
        log.debug(message);
    }
}
