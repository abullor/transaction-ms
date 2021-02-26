package com.reloadly.exposition;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.reloadly.enums.TransactionType;


public class SaveTransactionRequest {
	@NotNull
	private TransactionType transactionType;
	@NotNull
	private BigDecimal amount;
	
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "SaveTransactionRequest [transactionType=" + transactionType + ", amount=" + amount + "]";
	}
}