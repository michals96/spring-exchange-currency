package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.entities.Rate;
import com.currencyexchange.repositories.RateRepository;
import com.currencyexchange.services.CurrencyExchangeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    private final RateRepository ratesRepository;

    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService, RateRepository ratesRepository)
    {
        this.currencyExchangeService = currencyExchangeService;
        this.ratesRepository = ratesRepository;
    }

    public void addRatesToDatabase(CurrencyExchange currencyExchange){
        ratesRepository.save(new Rate(currencyExchange.getFirstCurrency(), currencyExchange.getSecondCurrency(), currencyExchange.getFactor(), currencyExchange.getDate()));
    }

    public Boolean canBeAddedToDB(CurrencyExchange currencyExchange){
        return ratesRepository.findByCurrenciesAndDate(currencyExchange.getFirstCurrency(), currencyExchange.getSecondCurrency(), currencyExchange.getDate()).isEmpty();
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
            addRatesToDatabase(currencyExchange);
        }

        return currencyExchange;
    }
}
