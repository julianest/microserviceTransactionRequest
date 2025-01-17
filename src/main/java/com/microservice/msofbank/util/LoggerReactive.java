package com.microservice.msofbank.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LoggerReactive {
    public <T> Mono<T> logInsert(Class<?> clazz, T entity) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return Mono.fromRunnable(() -> logger.info("Insertando entidad: {}", entity))
                .thenReturn(entity);
    }

    public <T> Mono<T> logUpdate(Class<?> clazz, T entity) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return Mono.fromRunnable(() -> logger.info("Actualizando entidad: {}", entity))
                .thenReturn(entity);
    }

    public <T> Mono<T> logDelete(Class<?> clazz, T entity) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return Mono.fromRunnable(() -> logger.info("Eliminando entidad: {}", entity))
                .thenReturn(entity);
    }
}
