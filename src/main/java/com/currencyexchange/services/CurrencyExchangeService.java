package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;

public interface CurrencyExchangeService {
    CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount);
}
