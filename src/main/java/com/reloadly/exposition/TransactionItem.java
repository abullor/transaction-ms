package com.reloadly.exposition;

import java.math.BigDecimal;

import com.reloadly.enums.TransactionType;

public class TransactionItem {
	private String id;
	private TransactionType transactionType;
	private Long accountId;
	private BigDecimal amount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transactionType=" + transactionType + ", accountId=" + accountId
				+ ", amount=" + amount + "]";
	}
}
