package com.currencyexchange.entities;

public class CurrencyExchange {
    private final long id;
    private final Double amount;
    private final String firstCurrency;
    private final String secondCurrency;
    public Double convertedAmount;

    public CurrencyExchange(long id, String firstCurrency, String secondCurrency, Double amount, Double convertedAmount) {
        this.id = id;
        this.amount = amount;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
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
