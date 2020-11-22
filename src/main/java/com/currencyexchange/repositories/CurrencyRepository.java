package com.currencyexchange.repositories;

import com.currencyexchange.entities.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    @Query("SELECT u FROM Currency u WHERE u.sourceCurrency = ?1 and u.targetCurrency = ?2")
    List<Currency> findByCurrencies(String sourceCurrency, String targetCurrency);
}
