package com.currencyexchange.restservice;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyExchangeController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/currencyExchange", method = RequestMethod.GET)
    public String currencyExchange( @RequestParam Map<String, String> requestParams) throws Exception {

        CurrencyExchange currencyExchange = new CurrencyExchange(counter.incrementAndGet(), requestParams.get("firstCurrency"), requestParams.get("secondCurrency"));

        currencyExchange.converter();

        return currencyExchange.responseString();
    }
}




