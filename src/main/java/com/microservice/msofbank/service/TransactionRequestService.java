package com.microservice.msofbank.service;

import com.microservice.msofbank.dto.TransactionRequestDTO;
import com.microservice.msofbank.repository.TransactionRequestRepository;
import com.microservice.msofbank.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRequestService {

    private final TransactionRequestRepository transactionRequestRepository;

    public TransactionRequestService(TransactionRequestRepository transactionRequestRepository) {
        this.transactionRequestRepository = transactionRequestRepository;
    }

    public Flux<TransactionRequestDTO> getAll(){
        return this.transactionRequestRepository.findAll()
                .map(EntityDtoUtil::toDto);

    }

    public Mono<TransactionRequestDTO> getTransactionRequestById(String id){
        return this.transactionRequestRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<TransactionRequestDTO> insertTransaction(Mono<TransactionRequestDTO> transactionRequestDTO){
        return transactionRequestDTO.map(EntityDtoUtil::toEntity)
                .flatMap(this.transactionRequestRepository::insert)
                .map(EntityDtoUtil::toDto);//se vuelve a convertir por que nuestro metodo devuelve un dto
    }

    public Mono<TransactionRequestDTO> updateTransaction(String id, Mono<TransactionRequestDTO> transactionRequestDTO){
        return this.transactionRequestRepository.findById(id)
                .flatMap(transactionRequest -> transactionRequestDTO.map(EntityDtoUtil::toEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(this.transactionRequestRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteTransaction(String id){
        return this.transactionRequestRepository.deleteById(id);
    }

}
