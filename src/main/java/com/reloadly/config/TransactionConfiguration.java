package com.reloadly.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reloadly.exposition.SaveTransactionRequest;
import com.reloadly.model.Transaction;

@Configuration
public class TransactionConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		PropertyMap<SaveTransactionRequest, Transaction> transactionMap = new PropertyMap<SaveTransactionRequest, Transaction>() {
			protected void configure() {
				skip(destination.getId());
			}
		};

		modelMapper.addMappings(transactionMap);

		return modelMapper;
	}
}