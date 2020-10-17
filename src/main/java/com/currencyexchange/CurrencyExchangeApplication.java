package com.currencyexchange;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.entities.Customer;
import com.currencyexchange.repositories.CrudCurrencyExchangeRepository;
import com.currencyexchange.repositories.CustomerRepository;
import com.currencyexchange.repositories.JavaCurrencyExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CurrencyExchangeApplication implements CommandLineRunner {
	@Autowired
	CustomerRepository repository;

	@Autowired
	CrudCurrencyExchangeRepository exchangeRepository;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}

	public void testCustomerDB(){
		repository.save(new Customer("Jack", "Bauer"));
		repository.save(new Customer("Chloe", "O'Brian"));
		repository.save(new Customer("Kim", "Bauer"));

		// fetch all customers
		for (Customer customer : repository.findAll()) {
			log.info(customer.toString());
		}
		log.info("");

		// fetch an individual customer by ID == 1
		Customer customer = repository.findById(1L);
		log.info(customer.toString());
		log.info("");

		// fetch customers by last name == bauer
		repository.findByLastName("Bauer").forEach(bauer -> {
			log.info(bauer.toString());
		});

	}
	public void testExchangeDB(){
		exchangeRepository.save(new CurrencyExchange("USD", "PLN", 100.0, 200.0, 3.0));
		exchangeRepository.save(new CurrencyExchange("GBP", "USD", 300.0, 600.0, 1.0));
	}

	@Override
	public void run(String... args) throws Exception {
		testCustomerDB();
		testExchangeDB();
	}
}
