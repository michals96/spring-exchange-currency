package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.services.CurrencyExchangeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;
    Class<?> clazz;

    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService)
    {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping(value = "/currencyExchange/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchange( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        CurrencyExchange currencyExchange = currencyExchangeService.convert(sourceCurrency, targetCurrency, amount);
        return currencyExchange;
    }

    @GetMapping(value = "/currencyExchangeWithAllApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public Map<String, CurrencyExchange> currencyExchangeWithAllApi(@PathVariable String sourceCurrency,
                                                             @PathVariable String targetCurrency,
                                                             @PathVariable Double amount) throws Exception {
        Map<String, CurrencyExchange> currencyExchange = currencyExchangeService.convertWithAllApi(sourceCurrency, targetCurrency, amount);
        return currencyExchange;
    }

    @GetMapping(value = "/currencyExchangeApi/{serviceType}/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchangeApi(@PathVariable String serviceType,
                                                @PathVariable String sourceCurrency,
                                                             @PathVariable String targetCurrency,
                                                             @PathVariable Double amount) throws Exception {
        CurrencyExchange currencyExchange = currencyExchangeService.convertWithApi(sourceCurrency, targetCurrency, amount, Class.forName("com.currencyexchange.repositories." + serviceType));
        return currencyExchange;
    }
}
