package com.currencyexchange.restservice;

import javax.money.CurrencyUnit;

public class CurrencyExchange {

    private final long id;
    private final Integer amount;
    private final String firstCurrency;
    private final String secondCurrency;

    public CurrencyExchange(long id, String firstCurrency, String secondCurrency) {
        this.id = id;
        this.amount = 100;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
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

    public Integer getAmount(){
        return amount;
    }
}