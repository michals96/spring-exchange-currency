package com.currencyexchange.entities;

import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="RATE")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String sourceCurrency, targetCurrency;
    @NonNull
    private Double rate;
    @NonNull
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="currency_id", nullable=false)
    private Currency currency;

    public Double getRate(){
        return this.rate;
    }

    public Rate(){
    }

    public Rate(String sourceCurrency, String targetCurrency, Double rate, LocalDate date){
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.date = date;
    }

    public Rate(String sourceCurrency, String targetCurrency, Double rate, LocalDate date, Currency currency){
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.date = date;
        this.currency = currency;
    }
}
