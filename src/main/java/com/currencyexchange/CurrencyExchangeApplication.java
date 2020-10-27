package com.currencyexchange;

import com.currencyexchange.entities.Customer;
import com.currencyexchange.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@Slf4j
public class CurrencyExchangeApplication implements CommandLineRunner {
	@Autowired
	CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}

	public void testCustomerDB(){
		repository.save(new Customer("Jack", "Bauer"));
		repository.save(new Customer("Chloe", "O'Brian"));
		repository.save(new Customer("Kim", "Bauer"));

		/* fetch all customers
		for (Customer customer : repository.findAll()) {
			log.info(customer.toString());
		}
		log.info("");

		fetch an individual customer by ID == 1
		Customer customer = repository.findById(1L);
		log.info(customer.toString());
		log.info("");

		fetch customers by last name == bauer
		repository.findByLastName("Bauer").forEach(bauer -> {
			log.info(bauer.toString());
		}); */

	}

	@Override
	public void run(String... args) throws Exception {
		testCustomerDB();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(("com.currencyexchange.controllers")))
				.paths(PathSelectors.any())
				.build();
	}
}
