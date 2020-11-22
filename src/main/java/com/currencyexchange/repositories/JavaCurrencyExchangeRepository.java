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

    private final CurrencyRepository currencyRepository;
    private final RateRepository rateRepository;

    public JavaCurrencyExchangeRepository(CurrencyRepository currencyRepository, RateRepository rateRepository){
        this.currencyRepository = currencyRepository;
        this.rateRepository = rateRepository;
    }

    public Currency getValidCurrency(String sourceCurrency, String targetCurrency){
        return currencyRepository.findByCurrencies(sourceCurrency, targetCurrency);
    }

    public Boolean ratesExistsInDatabase(List<Currency> validCurrenciesList){
        return true;
        //return !rateRepository.findByCurrenciesAndDate(sourceCurrency, targetCurrency, date).isEmpty();
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

        Currency validCurrency = getValidCurrency(sourceCurrency, targetCurrency);

        try{
            if(validCurrency == null){
                throw new Exception("Invalid currencies!");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }



        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(sourceCurrency, targetCurrency);

        return rate.getFactor();
    }
}
