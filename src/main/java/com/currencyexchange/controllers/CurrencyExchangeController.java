package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.repositories.CrudCurrencyExchangeRepository;
import com.currencyexchange.services.CurrencyExchangeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CurrencyExchangeController {

    private final CrudCurrencyExchangeRepository crudCurrencyExchangeRepository;

    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyExchangeController(CrudCurrencyExchangeRepository exchangeRepository, CurrencyExchangeService currencyExchangeService)
    {
        this.crudCurrencyExchangeRepository = exchangeRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    public void addCurrencyExchangeToDatabase(CurrencyExchange currencyExchange){
        crudCurrencyExchangeRepository.save(currencyExchange);
    }

    public Boolean canBeAddedToDB(CurrencyExchange currencyExchange){
        return crudCurrencyExchangeRepository.findByCurrenciesAndDate(currencyExchange.getFirstCurrency(), currencyExchange.getSecondCurrency(), currencyExchange.getDate()).isEmpty();
    }

    @GetMapping(value = "/currencyExchange/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchange( @PathVariable String sourceCurrency, @PathVariable String targetCurrency, @PathVariable Double amount) throws Exception {
        return currencyExchangeService.convert(sourceCurrency, targetCurrency, amount);
    }

    @GetMapping(value = "/currencyExchangeWithAllApi/{sourceCurrency}/{targetCurrency}/{amount}")
    public Map<String, CurrencyExchange> currencyExchangeWithAllApi(@PathVariable String sourceCurrency,
                                                             @PathVariable String targetCurrency,
                                                             @PathVariable Double amount) throws Exception {
        return currencyExchangeService.convertWithAllApi(sourceCurrency, targetCurrency, amount);
    }

    @GetMapping(value = "/currencyExchangeApi/{serviceType}/{sourceCurrency}/{targetCurrency}/{amount}")
    public CurrencyExchange currencyExchangeApi(@PathVariable String serviceType,
                                                @PathVariable String sourceCurrency,
                                                             @PathVariable String targetCurrency,
                                                             @PathVariable Double amount) throws Exception {
        CurrencyExchange currencyExchange = currencyExchangeService.convertWithApi(sourceCurrency, targetCurrency, amount, Class.forName("com.currencyexchange.repositories." + serviceType));

        if(canBeAddedToDB(currencyExchange)){
            addCurrencyExchangeToDatabase(currencyExchange);
        }

        return currencyExchange;
    }
}
