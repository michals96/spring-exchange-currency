package com.currencyexchange.repositories;

import javax.money.NumberValue;

public interface CurrencyExchangeRepository {
    NumberValue calculate(String sourceCurrency, String targetCurrency);
}
