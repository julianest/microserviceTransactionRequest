package com.microservice.msofbank.service;


import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    public <T> T addCreatedByAuditInfo(T obj, String authName) {
        return setAuditInfo(obj, "setCreatedBy", authName);
    }

    public <T> T addUpdatedByAuditInfo(T obj, String authName) {
        return setAuditInfo(obj, "setUpdatedBy", authName);
    }

    private <T> T setAuditInfo(T obj, String auditMethod, String authName) {
        String username = getCredentialsAuth(authName, 0);
        try {
            Method setAuditMethod = obj.getClass().getMethod(auditMethod, String.class);
            setAuditMethod.invoke(obj, username);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("El objeto no tiene el método adecuado o no se puede invocar", e);
        }
    }


    public String getCredentialsAuth(String authName, int part) {
        if (authName != null && authName.startsWith("Basic ")) {
            String base64Credentials = authName.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":", 2);

            return (part > 1) ? credentials : parts[part];

        }
        throw new RuntimeException("Authorization header es invalido o no encuentra el username");
    }


}
