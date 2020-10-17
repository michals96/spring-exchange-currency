package com.currencyexchange.repositories;

import com.currencyexchange.entities.CurrencyExchange;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CrudCurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long>{
    List<CurrencyExchange> findByFirstCurrency(String firstCurrency);
    List<CurrencyExchange> findBySecondCurrency(String secondCurrency);
    List<CurrencyExchange> findByDate(LocalDate date);
    CurrencyExchange findById(long id);

    @Query("SELECT u FROM CurrencyExchange u WHERE u.firstCurrency = ?1 and u.secondCurrency = ?2 and u.date = ?3")
    List<CurrencyExchange> findByCurrenciesAndDate(String firstCurrency, String secondCurrency, LocalDate date);
}
