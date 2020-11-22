package com.currencyexchange.controllers;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.entities.Rate;
import com.currencyexchange.repositories.RateRepository;
import com.currencyexchange.services.CurrencyExchangeService;
import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
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

    public Boolean ratesExistsInDatabase(String sourceCurrency, String targetCurrency, LocalDate date){
        return !ratesRepository.findByCurrenciesAndDate(sourceCurrency, targetCurrency, date).isEmpty();
    }

    // do usuniecia - mozna bezposrednio z repozytorium skorzystac
    public Double fetchRate(String sourceCurrency, String targetCurrency, LocalDate date){
        return ratesRepository.findByCurrenciesAndDate(sourceCurrency, targetCurrency, date).get(0).getRate();
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
        // ten if else to repozytorium
        if(ratesExistsInDatabase(sourceCurrency, targetCurrency, LocalDate.now())){

            Double rate = fetchRate(sourceCurrency, targetCurrency, LocalDate.now());
            Double convertedAmount = amount * rate;
            return new CurrencyExchange(0, sourceCurrency, targetCurrency, 100.0, convertedAmount, rate, LocalDate.now());
        }
        else{

            CurrencyExchange currencyExchange = currencyExchangeService.convertWithApi(sourceCurrency, targetCurrency, amount, Class.forName("com.currencyexchange.repositories." + serviceType));
            addRatesToDatabase(currencyExchange);
            return currencyExchange;
        }
        // tutaj jeden return zamiast dwoch w instrukcji if else
    }
}

// Example invocation: http://localhost:8080/currencyExchangeApi/JavaCurrencyExchangeRepository/USD/GBP/100.0
// DB Access: http://localhost:8080/h2-console

// Tabela do currencies(statycznie zawiera wszystkie waluty które ja zezwalam, jesli nie ma to exceptiem) -> w tabeli Rates przetrzymywać ID currency
// Zapytanie do bazy/do api @Transactional na poziomie serwisu który korzysta z dwóch różnych repozytoriów