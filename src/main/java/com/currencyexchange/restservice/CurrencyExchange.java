package com.currencyexchange.restservice;

import javax.money.NumberValue;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

public class CurrencyExchange {

    private final long id;
    private final Double amount;
    private final String firstCurrency;
    private final String secondCurrency;

    public Double convertedAmount;

    public CurrencyExchange(long id, String firstCurrency, String secondCurrency) {
        this.id = id;
        this.amount = 100.0;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.convertedAmount = 0.0;
    }

    public void converter()
    {
        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(getFirstCurrency(), getSecondCurrency());

        NumberValue factor = rate.getFactor();

        convertedAmount = Double.parseDouble(factor.toString()) * amount;
    }

    public long getId() {
        return id;
    }

    public String getFirstCurrency(){
        return firstCurrency;
    }

    public String getSecondCurrency(){
        return secondCurrency;
    }

    public Double getAmount(){
        return amount;
    }

    public Double getConvertedAmount(){
        return convertedAmount;
    }

    public String responseString() {
        return getId() + ": " + getAmount() + " " + getFirstCurrency() + " = " + getConvertedAmount() + " " + getSecondCurrency();
    }
}