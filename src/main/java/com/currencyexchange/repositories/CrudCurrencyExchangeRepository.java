package com.currencyexchange.repositories;

import com.currencyexchange.entities.CurrencyExchange;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudCurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long>{
    List<CurrencyExchange> findByFirstCurrency(String firstCurrency);
    List<CurrencyExchange> findBySecondCurrency(String secondCurrency);
    CurrencyExchange findById(long id);
}
