package com.github.brianmmcclain.pfsretailinventory;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PfsRetailInventoryApplication {

	@Autowired
	ProductRepository productRepo;

	@Autowired
    TagRepository tagRepo;

	ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(PfsRetailInventoryApplication.class, args);
	}

	@Bean
	public Function<String, String> inventory() {
		return s -> {
			try {
				InventoryAction action = objectMapper.readValue(s, InventoryAction.class);
				return action.execute(productRepo, tagRepo);
			} catch (JsonParseException e) { 
				return "JSON Parse Exception";
			} catch (JsonMappingException e) {
				return "JSON Mapping Exception";
			} catch (IOException e) {
				return "IO Exception";
			}
		};
	}

}
