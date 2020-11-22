package com.currencyexchange.repositories;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.money.NumberValue;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Repository
@Component("javaCurrencyExchangeRepository")
public class JavaCurrencyExchangeRepository implements CurrencyExchangeRepository {

    @Override
    public NumberValue calculate(String sourceCurrency, String targetCurrency) {

        /*
        1. Check if those values can be exchanged
            a) Send query to currencies database:
                YES: go futher
                NO:  exception
        2. Check if those values have rate in Rates table
        3. If in rates: Use Rates
        4. Else: fetch rate and return
        */

        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(sourceCurrency, targetCurrency);

        return rate.getFactor();
    }
}
