package com.currencyexchange.services;

import com.currencyexchange.entities.Rate;

import java.util.List;

public interface RateService {
    Rate fetchRate(List<Rate> ratesList);
    Boolean rateExists(String sourceCurrency, String targetCurrency);
}
