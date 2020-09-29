package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.services.CurrencyExchangeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService)
    {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping(value = "/currencyExchange/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchange( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        CurrencyExchange currencyExchange = currencyExchangeService.convert(sourceCurrency, targetCurrency, amount);
        return currencyExchange;
    }

    @GetMapping(value = "/currencyExchangeApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public Map<String, CurrencyExchange> currencyExchangeApi(@PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        Map<String, CurrencyExchange> currencyExchange = currencyExchangeService.convertWithAllApi(sourceCurrency, targetCurrency, amount);
        return currencyExchange;
    }
}
