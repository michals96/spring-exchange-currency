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
    //private

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String sourceCurrency, targetCurrency;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private List<Rate> rates;

    public Currency(String sourceCurrency, String targetCurrency) throws Exception {
        List<String> allowedCurrencies = List.of("USD", "PLN");

        if (allowedCurrencies.contains(sourceCurrency) && allowedCurrencies.contains(targetCurrency)){
            this.sourceCurrency = sourceCurrency;
            this.targetCurrency = targetCurrency;
        }
        else{
            throw new Exception("Invalid currency!");
        }

    }
}

