package com.currencyexchange.repositories;

import org.springframework.stereotype.Repository;

import javax.money.NumberValue;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Repository
public class JavaCurrencyExchangeRepository implements CurrencyExchangeRepository{

    @Override
    public NumberValue calculate(String sourceCurrency, String targetCurrency) {

        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(sourceCurrency, targetCurrency);

        return rate.getFactor();
    }
}
