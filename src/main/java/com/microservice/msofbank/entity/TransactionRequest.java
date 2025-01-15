package com.microservice.msofbank.entity;

import com.microservice.msofbank.util.TypeAccount;
import com.microservice.msofbank.util.TypeTransaction;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class TransactionRequest {

    @Id
    private String id;
    private String numberAccount;
    private double amount;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;
    @CreatedDate
    private LocalDateTime createdAt;
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String updatedBy;
}
