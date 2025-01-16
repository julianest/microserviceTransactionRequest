package com.microservice.msofbank.controller;

import com.microservice.msofbank.dto.TransactionRequestDTO;
import com.microservice.msofbank.service.TransactionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transact")
public class TransactionRequestController {

    @Autowired
    private TransactionRequestService transactionRequestService;


    @PostMapping("/process/{numberAccount}")
    public Mono<TransactionRequestDTO> processTransaction(@RequestHeader HttpHeaders headers, @PathVariable String numberAccount, @RequestBody Mono<TransactionRequestDTO> transactionRequestDTO) {
        return transactionRequestService.processTransaction(headers, numberAccount, transactionRequestDTO);
    }

    @GetMapping("/all")
    public Flux<TransactionRequestDTO> all(){
        return this.transactionRequestService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionRequestDTO>> getTransactionById(@PathVariable String id){
        return this.transactionRequestService.getTransactionRequestById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionRequestDTO> insertTransaction(@RequestBody Mono<TransactionRequestDTO> transactionRequestDTO){
        return this.transactionRequestService.insertTransaction(transactionRequestDTO);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TransactionRequestDTO>> updateTransaction(@PathVariable String id, @RequestBody Mono<TransactionRequestDTO> transactionRequestDTO){
        return this.transactionRequestService.updateTransaction(id, transactionRequestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTransaction(@PathVariable String id){
        return this.transactionRequestService.deleteTransaction(id);
    }

}
