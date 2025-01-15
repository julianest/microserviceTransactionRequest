package com.microservice.msofbank.entity;

import com.microservice.msofbank.util.TypeAccount;
import com.microservice.msofbank.util.TypeTransaction;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class TransactionRequest {

    @Id
    private String id;

    private double amount;

    private TypeTransaction typeTransaction;

    private TypeAccount typeAccount;
}
