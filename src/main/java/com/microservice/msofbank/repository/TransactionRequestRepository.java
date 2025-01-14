package com.microservice.msofbank.repository;

import com.microservice.msofbank.entity.TransactionRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRequestRepository extends ReactiveMongoRepository <TransactionRequest,String> {

}
