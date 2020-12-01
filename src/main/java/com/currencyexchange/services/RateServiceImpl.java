package com.currencyexchange.services;

import com.currencyexchange.entities.Rate;
import com.currencyexchange.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("rateService")
public class RateServiceImpl implements RateService{

    private final RateRepository rateRepository;

    @Autowired
    RateServiceImpl(RateRepository rateRepository){
        this.rateRepository = rateRepository;
    }

    @Override
    public List<Rate> fetchRate(String sourceCurrency, String targetCurrency) {
        return rateRepository.findByCurrenciesAndDate(sourceCurrency, targetCurrency, LocalDate.now());
    }

    @Override
    public Boolean rateExists(String sourceCurrency, String targetCurrency) {
        return !rateRepository.findByCurrenciesAndDate(sourceCurrency, targetCurrency, LocalDate.now()).isEmpty();
    }

}
