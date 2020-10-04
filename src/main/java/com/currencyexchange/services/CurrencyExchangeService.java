package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import org.json.JSONException;
import java.io.IOException;
import java.util.Map;

public interface CurrencyExchangeService {
    CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount);
    Map<String, CurrencyExchange> convertWithAllApi(String sourceCurrency, String targetCurrency, Double amount);
    CurrencyExchange convertWithApi(String sourceCurrency, String targetCurrency, Double amount, Class<?> clazz);
    // Hit the api. New endpoint in controller. Specify which api do you want to hit.
}
