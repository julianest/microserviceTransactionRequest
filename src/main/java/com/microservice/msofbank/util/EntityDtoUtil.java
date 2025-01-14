package com.microservice.msofbank.util;

import com.microservice.msofbank.dto.TransactionRequestDTO;
import com.microservice.msofbank.entity.TransactionRequest;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static TransactionRequestDTO toDto(TransactionRequest transactionRequest){
        TransactionRequestDTO dto = new TransactionRequestDTO();
        BeanUtils.copyProperties(transactionRequest, dto);
        return dto;
    }

    public static TransactionRequest toEntity(TransactionRequestDTO transactionRequestDTO){
        TransactionRequest transactionRequest = new TransactionRequest();
        BeanUtils.copyProperties(transactionRequest, transactionRequestDTO);
        return transactionRequest;
    }
}
