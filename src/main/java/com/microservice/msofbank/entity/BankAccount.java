package com.microservice.msofbank.entity;
import com.microservice.msofbank.util.TypeAccount;
import lombok.*;


import java.time.LocalDateTime;

//Mapeo de la clase BankAccount de la aplicacion.
@Data
public class BankAccount {

    private Long idBankAccount;
    private String numberAccount;
    private TypeAccount typeAccount;
    private double balance;
    private double interestAccumulated;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updateAt;
    private String updateBy;

}
