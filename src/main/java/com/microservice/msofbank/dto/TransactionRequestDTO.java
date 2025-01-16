package com.microservice.msofbank.dto;

import com.microservice.msofbank.util.TypeAccount;
import com.microservice.msofbank.util.TypeTransaction;
import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    private String id;
    private String numberAccount;
    private double balance;
    private double amount;
    //private double finalBalance;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}







