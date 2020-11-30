package com.currencyexchange.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="RATE")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // id dla kazdej currency
    //@NonNull
    //private Currency sourceCurrency;//, targetCurrency; // oba typu Currency
    @NonNull
    private Double rate;
    @NonNull
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="source_currency_id")
    private Currency sourceCurrency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="target_currency_id")
    private Currency targetCurrency;

    public Rate(Currency sourceCurrency, Currency targetCurrency, Double rate, LocalDate date){
        this.rate = rate;
        this.date = date;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }
}
