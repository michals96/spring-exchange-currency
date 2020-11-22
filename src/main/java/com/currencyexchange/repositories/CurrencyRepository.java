package com.currencyexchange.repositories;

import com.currencyexchange.entities.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
