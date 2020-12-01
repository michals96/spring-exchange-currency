package com.currencyexchange.services;

import com.currencyexchange.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("currencyServiceImpl")
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    CurrencyServiceImpl(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Boolean validConversion(String sourceCurrency, String targetCurrency) {
        return (currencyRepository.findByCurrency(sourceCurrency) != null && currencyRepository.findByCurrency(targetCurrency) != null);
    }
}
