package com.microservice.msofbank.service;

import com.microservice.msofbank.dto.TransactionRequestDTO;
import com.microservice.msofbank.entity.BankAccount;
import com.microservice.msofbank.entity.TransactionRequest;
import com.microservice.msofbank.repository.TransactionRequestRepository;
import com.microservice.msofbank.util.EntityDtoUtil;
import com.microservice.msofbank.util.TypeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class TransactionRequestService {

    @Autowired
    private TransactionRequestRepository transactionRequestRepository;

    @Autowired
    private AuthService authService;


    private final WebClient webClient;

    public TransactionRequestService(WebClient.Builder webClientBuilder, TransactionRequestRepository transactionRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/accounts")
                .build();
    }


    public Mono<TransactionRequestDTO> processTransaction(HttpHeaders headersRequest, String numberAccount, Mono<TransactionRequestDTO> transactionRequestDTO) {
        String authName = headersRequest.getFirst(HttpHeaders.AUTHORIZATION);

        return transactionRequestDTO.flatMap(request ->
                webClient.get()
                        .uri("/{numberAccount}", numberAccount)
                        .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, authName))
                        .retrieve()
                        .bodyToMono(BankAccount.class) // Mapea la respuesta a tu entidad de cuenta bancaria
                        .onErrorResume(error -> {
                            return Mono.error(new RuntimeException("Error revisando account detalles: " + error.getMessage()));
                        })
                        .flatMap(account -> {
                            // Operaciones
                            if (request.getTypeTransaction() == TypeTransaction.DEPOSIT) {
                                account.setBalance(account.getBalance() + request.getAmount());
                            } else if (request.getTypeTransaction() == TypeTransaction.WITHDRAW) {
                                if (account.getBalance() < request.getAmount()) {
                                    return Mono.error(new RuntimeException("Insufficient funds"));
                                }
                                account.setBalance(account.getBalance() - request.getAmount());
                            }
                            // Persistencia en MongoDB
                            TransactionRequest transaction = new TransactionRequest();
                            transaction.setNumberAccount(request.getNumberAccount());
                            transaction.setBalance(account.getBalance());
                            transaction.setAmount(request.getAmount());
                            transaction.setTypeTransaction(request.getTypeTransaction());
                            transaction.setTypeAccount(request.getTypeAccount());
                            transaction = (transaction.getCreatedBy() == null)
                                    ? authService.addCreatedByAuditInfo(transaction, authName)
                                    : authService.addUpdatedByAuditInfo(transaction, authName);
                            //Persistencia en MongoDB
                            return transactionRequestRepository.save(transaction)
                                    .map(saved -> new TransactionRequestDTO(
                                            saved.getId(), account.getNumberAccount(), account.getBalance(), saved.getAmount(),
                                            saved.getTypeTransaction(), saved.getTypeAccount(),
                                            saved.getCreatedAt(), saved.getCreatedBy(), saved.getUpdatedAt(), saved.getUpdatedBy())
                                    );
                        })
        );
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
