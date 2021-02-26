package com.reloadly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reloadly.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
}