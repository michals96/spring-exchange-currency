package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.repositories.CurrencyExchangeRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("currencyExchangeApiService")
public class CurrencyExchangeApiServiceImpl implements CurrencyExchangeService {

    @Autowired
    @Qualifier("javaCurrencyExchangeApiRepository")
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Override
    public CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount) throws InterruptedException, JSONException, IOException {
        Double factor = (Double) this.currencyExchangeRepository.calculate(sourceCurrency, targetCurrency);
        Double convertedAmount = factor * amount;

        return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
    }
}
