package com.currencyexchange.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="CURRENCY")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String currency;

    @Fetch(value = FetchMode.SELECT)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceCurrency", cascade = CascadeType.ALL)
    private List<Rate> sourceRates;

    @Fetch(value = FetchMode.SELECT)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "targetCurrency", cascade = CascadeType.ALL)
    private List<Rate> targetRates;

    public Currency(String sourceCurrency) throws Exception {
        List<String> allowedCurrencies = List.of("USD", "PLN");

        if (allowedCurrencies.contains(sourceCurrency)){
            this.currency = sourceCurrency;
        }
        else{
            throw new Exception("Invalid currency!");
        }
    }

    @Override
    public String toString() {
        return currency;
    }
}

