package com.reloadly.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.reloadly.enums.TransactionType;
import com.reloadly.exposition.SaveTransactionRequest;
import com.reloadly.exposition.SaveTransactionResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("A transaction")
public class TransactionControllerSpec {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/transactions");
	}

	@Nested
    @DisplayName("is trying to be processed with correct data")
	class ProcessCorrectTransaction {
		@Test
		public void save() throws Exception {
			SaveTransactionRequest request = new SaveTransactionRequest();
			request.setAmount(new BigDecimal(System.currentTimeMillis()));
			request.setTransactionType(TransactionType.DEBIT);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjE0Mjg1ODk0LCJpYXQiOjE2MTQyODQ5OTR9.0AR6ClF1nO8o-zEbmMu-848rO6ylEYT1heBKX8OKCEGYpiDzFGGEcIn82ieHr8WrJugSE_GHxQBR2iR3Asuheg");
	
			HttpEntity<SaveTransactionRequest> requestEntity = new HttpEntity<>(request, headers);
	
			ResponseEntity<SaveTransactionResponse> responseEntity = template.postForEntity(base.toURI(), requestEntity, SaveTransactionResponse.class);
			
			assertEquals(200, responseEntity.getStatusCodeValue());
			assertNotNull(responseEntity.getBody().getId());
		}
	}
}