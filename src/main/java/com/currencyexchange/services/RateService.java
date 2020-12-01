package com.currencyexchange.services;

import com.currencyexchange.entities.Rate;

import java.util.List;

public interface RateService {
    List<Rate> fetchRate(String sourceCurrency, String targetCurrency);
    Boolean rateExists(String sourceCurrency, String targetCurrency);
}
