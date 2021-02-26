package com.reloadly.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reloadly.exposition.FindResponse;
import com.reloadly.exposition.SaveTransactionRequest;
import com.reloadly.exposition.SaveTransactionResponse;
import com.reloadly.exposition.TransactionItem;
import com.reloadly.model.Transaction;
import com.reloadly.service.TransactionService;

@RestController
public class TransactionController {
	private final TransactionService transactionService;
	@Autowired
	private ModelMapper modelMapper;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/transactions")
	public SaveTransactionResponse save(@RequestBody @Valid SaveTransactionRequest request) {
		return modelMapper.map(this.transactionService.save(modelMapper.map(request, Transaction.class)),
				SaveTransactionResponse.class);
	}

	@GetMapping("/transactions")
	public FindResponse<TransactionItem> findByAccountId(@RequestParam(required = true) Long accountId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable paging = PageRequest.of(page, size);

		Page<Transaction> transactionsPage = this.transactionService.findByAccountId(accountId, paging);
		
		FindResponse<TransactionItem> response = new FindResponse<TransactionItem>();
		response.setPageNumber(transactionsPage.getNumber());
		response.setTotalPages(transactionsPage.getTotalPages());
		response.setTotalElements(transactionsPage.getTotalElements());
		
		response.setElements(transactionsPage.stream().map(transaction -> 
			modelMapper.map(transaction, TransactionItem.class)).collect(Collectors.toList()));

		return response;
	}
}