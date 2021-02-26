package com.reloadly.exposition;

public class SaveTransactionResponse {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SaveTransactionResponse [id=" + id + "]";
	}
}