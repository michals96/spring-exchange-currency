package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.services.CurrencyExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService)
    {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping(value = "/currencyExchangeNoApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchangeNoApi( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        return currencyExchangeService.monetaryConvert(sourceCurrency, targetCurrency, amount);
    }

    @GetMapping(value = "/currencyExchangeApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchangeApi( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        return currencyExchangeService.convert(sourceCurrency, targetCurrency, amount);
    }

    @GetMapping(value = "/currencyExchangeWithAllApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public Map<String, CurrencyExchange> currencyExchangeWithAllApi(@PathVariable String sourceCurrency,
                                                             @PathVariable String targetCurrency,
                                                             @PathVariable Double amount) throws Exception {
        return currencyExchangeService.convertWithAllApi(sourceCurrency, targetCurrency, amount);
    }

    @GetMapping(value = "/currencyExchangeSelectedApi/{serviceType}/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchangeSelectedApi(@PathVariable String serviceType,
                                                @PathVariable String sourceCurrency,
                                                             @PathVariable String targetCurrency,
                                                             @PathVariable Double amount) throws Exception {

            CurrencyExchange currencyExchange = currencyExchangeService.convertWithApi(sourceCurrency, targetCurrency, amount, Class.forName("com.currencyexchange.repositories." + serviceType));
            return currencyExchange;
    }
}

// Example invocation:
// a) http://localhost:8080/currencyExchangeSelectedApi/JavaCurrencyExchangeRepository/USD/GBP/100.0
// b) http://localhost:8080/currencyExchangeNoApi/USD/PLN/100.0
// DB Access: http://localhost:8080/h2-console
