package com.currencyexchange;

import com.currencyexchange.entities.Currency;
import com.currencyexchange.entities.Customer;
import com.currencyexchange.entities.Rate;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.repositories.CustomerRepository;
import com.currencyexchange.repositories.RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@EnableSwagger2
@SpringBootApplication
@Slf4j
@EnableJpaRepositories
public class CurrencyExchangeApplication implements CommandLineRunner {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	RateRepository rateRepository;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}

	public void testCustomerDB(){
		customerRepository.save(new Customer("Jack", "Bauer"));
		customerRepository.save(new Customer("Chloe", "O'Brian"));
		customerRepository.save(new Customer("Kim", "Bauer"));

		//fetch all customers
		for (Customer customer : customerRepository.findAll()) {
			log.info(customer.toString());
		}
		log.info("");

		//fetch an individual customer by ID == 1
		Customer customer = customerRepository.findById(1L);
		log.info(customer.toString());
		log.info("");

		//fetch customers by last name == bauer
		customerRepository.findByLastName("Bauer").forEach(bauer -> {
			log.info(bauer.toString());
		});
	}

	public void fillCurrenciesTable() throws Exception {
		Currency firstCurrency = new Currency("USD");
		Currency secondCurrency = new Currency("PLN");

		currencyRepository.save(firstCurrency);
		currencyRepository.save(secondCurrency);

		rateRepository.save(new Rate(firstCurrency, secondCurrency, 0.3, LocalDate.parse("2017-02-16")));
		rateRepository.save(new Rate(secondCurrency, firstCurrency, 3.7, LocalDate.parse("2017-02-17")));

		/*for (Currency currency : currencyRepository.findAll()) {
			log.info(currency.getRates().get(1).getRate().toString());
		}
		log.info("");*/
	}

	@Override
	public void run(String... args) throws Exception {
		//testCustomerDB();
		fillCurrenciesTable();
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
