package com.currencyexchange.repositories;

import com.currencyexchange.entities.Currency;
import com.currencyexchange.entities.Rate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.money.NumberValue;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.util.List;

@Repository
@Component("javaCurrencyExchangeRepository")
public class JavaCurrencyExchangeRepository implements CurrencyExchangeRepository {

    @Override
    public NumberValue calculate(String sourceCurrency, String targetCurrency) {

        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(sourceCurrency, targetCurrency);

        return rate.getFactor();
    }
}
