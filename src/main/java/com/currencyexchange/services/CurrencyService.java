package com.currencyexchange.services;

public interface CurrencyService {
    Boolean validConversion(String sourceCurrency, String targetCurrency);
}
