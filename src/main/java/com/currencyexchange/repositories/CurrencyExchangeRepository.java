package com.currencyexchange.repositories;

import org.json.JSONException;

import javax.money.NumberValue;
import java.io.IOException;

public interface CurrencyExchangeRepository<T> {
    T calculate(String sourceCurrency, String targetCurrency) throws IOException, InterruptedException, JSONException;
}
