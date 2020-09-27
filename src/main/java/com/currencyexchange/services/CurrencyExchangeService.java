package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import org.json.JSONException;
import java.io.IOException;

public interface CurrencyExchangeService {
    CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount) throws InterruptedException, JSONException, IOException;
}
