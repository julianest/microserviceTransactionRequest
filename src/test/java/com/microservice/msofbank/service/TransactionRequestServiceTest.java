/*package com.microservice.msofbank.service;

import com.microservice.msofbank.dto.TransactionRequestDTO;
import com.microservice.msofbank.entity.BankAccount;
import com.microservice.msofbank.entity.TransactionRequest;
import com.microservice.msofbank.repository.TransactionRequestRepository;
import com.microservice.msofbank.util.TypeAccount;
import com.microservice.msofbank.util.TypeTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRequestServiceTest {
    @Mock
    private TransactionRequestRepository transactionRequestRepository;

    @Mock
    private AuthService authService;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private TransactionRequestService transactionRequestService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClientMock);
        transactionRequestService = new TransactionRequestService(webClientBuilder, transactionRequestRepository);
    }

    @Test
    void processTransaction_depositTransaction_success_withWebTestClient() {
        // Arrange
        String authName = "Basic dXNlcjpwYXNz"; // Base64 encoding of "user:pass"
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authName);
        String numberAccount = "12345";

        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAmount(100);
        requestDTO.setTypeTransaction(TypeTransaction.DEPOSIT);
        requestDTO.setTypeAccount(TypeAccount.CORRIENTE);

        BankAccount mockAccount = new BankAccount();
        mockAccount.setNumberAccount(numberAccount);
        mockAccount.setBalance(500);

        TransactionRequestDTO expectedResponse = new TransactionRequestDTO(
                "txn123", numberAccount, 600, 100, TypeTransaction.DEPOSIT, TypeAccount.CORRIENTE,
                null, "user", null, null
        );

        // Act & Assert
        webTestClient.post()
                .uri("/transactions")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BankAccount.class)
                .value(response -> {
                    assertEquals(mockAccount.getNumberAccount(), response.getNumberAccount());
                    assertEquals(600, response.getBalance()); // Assuming balance was updated to 600
                });
    }


    @Test
    void getAll_returnsTransactionList() {
        // Arrange
        TransactionRequest mockTransaction1 = new TransactionRequest();
        mockTransaction1.setId("txn1");
        mockTransaction1.setNumberAccount("12345");
        mockTransaction1.setBalance(500);

        TransactionRequest mockTransaction2 = new TransactionRequest();
        mockTransaction2.setId("txn2");
        mockTransaction2.setNumberAccount("67890");
        mockTransaction2.setBalance(1000);

        when(transactionRequestRepository.findAll()).thenReturn(Flux.just(mockTransaction1, mockTransaction2));

        // Act
        Flux<TransactionRequestDTO> result = transactionRequestService.getAll();

        // Assert
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void deleteTransaction_success() {
        // Arrange
        String transactionId = "txn123";
        when(transactionRequestRepository.deleteById(transactionId)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = transactionRequestService.deleteTransaction(transactionId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();
    }
}*/
