package com.currencyexchange.repositories;

import com.currencyexchange.entities.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    @Query("SELECT u FROM Currency u WHERE u.currency = ?1")
    Currency findByCurrency(String currency);
}
