package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.repositories.CurrencyExchangeRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.money.NumberValue;
import java.io.IOException;

@Service("currencyExchangeServiceImpl")
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    @Autowired
    @Qualifier("javaCurrencyExchangeRepository")
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Override
    public CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount) throws InterruptedException, JSONException, IOException {

        NumberValue factor = (NumberValue) this.currencyExchangeRepository.calculate(sourceCurrency, targetCurrency);
        Double convertedAmount = Double.parseDouble(factor.toString()) * amount;

        return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
    }
}
