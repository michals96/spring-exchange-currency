package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.services.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyExchangeController {

    @Autowired
    @Qualifier("currencyExchangeService")
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    @Qualifier("currencyExchangeApiService")
    private CurrencyExchangeService currencyExchangeApiService;

    @GetMapping(value = "/currencyExchange/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchange( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        CurrencyExchange currencyExchange = currencyExchangeService.convert(sourceCurrency, targetCurrency, amount);
        return currencyExchange;
    }

    @GetMapping(value = "/currencyExchangeApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchangeApi( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        CurrencyExchange currencyExchange = currencyExchangeApiService.convert(sourceCurrency, targetCurrency, amount);
        return currencyExchange;
    }
}
