package com.currencyexchange.repositories;

import com.currencyexchange.entities.Currency;
import com.currencyexchange.entities.Rate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Long> {


    //@Query("INSERT INTO RATE (DATE, RATE, SOURCE_CURRENCY, TARGET_CURRENCY, CURRENCY_ID) VALUES ('2017-02-16', 3.8, 'USD', 'GBP', 1)")
    //void insertRate();
    @Query("SELECT u FROM Rate u WHERE u.sourceCurrency = ?1 and u.targetCurrency = ?2 and u.date = ?3")
    List<Rate> findByCurrenciesAndDate(String sourceCurrency, String targetCurrency, LocalDate date);

    List<Rate> findByCurrency(Currency currency, Sort sort);

    /*@PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertRateWithEntityManager(Rate rate){
        this.entityManager.persist(rate);
    }*/

}
