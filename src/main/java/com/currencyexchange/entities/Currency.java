package com.currencyexchange.entities;

import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString
@Table(name="CURRENCY")
public class Currency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String sourceCurrency, targetCurrency;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="currency", cascade = CascadeType.ALL)
    @Fetch(value= FetchMode.SELECT)
    private List<Rate> rates;

    public Currency(){
    }

    public Currency(String sourceCurrency, String targetCurrency){
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public List<Rate> getRates(){
        return rates;
    }
}
