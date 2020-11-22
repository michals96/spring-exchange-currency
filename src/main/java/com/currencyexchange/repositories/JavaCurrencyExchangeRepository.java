package com.currencyexchange.repositories;

import com.currencyexchange.entities.Currency;
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

    private final CurrencyRepository currencyRepository;
    private final RateRepository rateRepository;

    public JavaCurrencyExchangeRepository(CurrencyRepository currencyRepository, RateRepository rateRepository){
        this.currencyRepository = currencyRepository;
        this.rateRepository = rateRepository;
    }

    public List<Currency> currenciesAreValid(String sourceCurrency, String targetCurrency){
        List<Currency> byCurrencies = currencyRepository.findByCurrencies(sourceCurrency, targetCurrency);
        return byCurrencies;
    }


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
        System.out.println(currenciesAreValid(sourceCurrency,targetCurrency).isEmpty());
        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(sourceCurrency, targetCurrency);

        return rate.getFactor();
    }
}
