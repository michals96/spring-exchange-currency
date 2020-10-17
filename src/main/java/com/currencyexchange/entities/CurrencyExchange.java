package com.currencyexchange.entities;

import java.time.LocalDate;

public class CurrencyExchange {
    private final long id;
    private final Double amount;
    private final String firstCurrency;
    private final String secondCurrency;

    public Double getFactor() {
        return factor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    private final Double factor;
    private final LocalDate date;
    public Double convertedAmount;

    public CurrencyExchange(long id, String firstCurrency, String secondCurrency, Double amount, Double convertedAmount, Double factor, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.factor = factor;
        this.date = date;
        this.convertedAmount = convertedAmount;
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
}
