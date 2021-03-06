package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;

import java.util.Map;

public interface CurrencyExchangeService {
    CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount);
    CurrencyExchange monetaryConvert(String sourceCurrency, String targetCurrency, Double amount);
    Map<String, CurrencyExchange> convertWithAllApi(String sourceCurrency, String targetCurrency, Double amount);
    CurrencyExchange convertWithApi(String sourceCurrency, String targetCurrency, Double amount, Class<?> clazz);
}
