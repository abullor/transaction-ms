package com.reloadly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.MessagingException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reloadly.model.Transaction;
import com.reloadly.repository.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository repository;
	private final RabbitMessagingTemplate template;
	@Value("${app.queue}")
	private String queue;
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
	
	public TransactionService(TransactionRepository repository, RabbitMessagingTemplate template) {
		this.repository = repository;
		this.template = template;
	}
	
	public Transaction save(Transaction transaction) {
		LOGGER.info(String.format("Authenticated accountId is [%s].", SecurityContextHolder.getContext().getAuthentication().getName()));
		
		transaction.setAccountId(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));
		Transaction txn = this.repository.save(transaction); 
		
		this.generateNotification(transaction, "txn_processed");
		
		return txn;
	}
	
	public Page<Transaction> findByAccountId(Long accountId, Pageable pageable) {
		return this.repository.findByAccountId(accountId, pageable);
	}
	
	private void generateNotification(Transaction transaction, String event) {
		StringBuilder message = new StringBuilder(transaction.getAccountId().toString()).append(",").append(event);
		
		try {
			template.convertAndSend(queue, message.toString());
		} catch (MessagingException e) {
			LOGGER.error("Notification failed.", e);
		}
	}
}