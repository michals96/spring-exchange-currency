package com.currencyexchange.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String firstCurrency, secondCurrency;
    @NonNull
    private Double amount, convertedAmount, factor;
    @NonNull
    private LocalDate date;
}
