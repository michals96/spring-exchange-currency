package com.currencyexchange.repositories;

import com.currencyexchange.entities.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Long> {
    @Query("SELECT u FROM Rate u WHERE u.sourceCurrency = ?1 and u.targetCurrency = ?2 and u.date = ?3")
    List<Rate> findByCurrenciesAndDate(String sourceCurrency, String targetCurrency, LocalDate date);
}
