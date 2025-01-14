package com.microservice.msofbank.dto;

import com.microservice.msofbank.util.TypeAccount;
import com.microservice.msofbank.util.TypeTransaction;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class TransactionRequestDTO {

    private String id;
    private double amount;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;

}







