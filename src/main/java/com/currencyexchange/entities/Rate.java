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
    @NonNull
    private String sourceCurrency, targetCurrency;
    @NonNull
    private Double rate;
    @NonNull
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="currency_id", nullable=false)
    private Currency currency;

    public Rate(String sourceCurrency, String targetCurrency, Double rate, LocalDate date, Currency currency){
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.date = date;
        this.currency = currency;
    }
}
