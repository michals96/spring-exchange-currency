package com.currencyexchange.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="CURRENCY")
public class Currency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String sourceCurrency, targetCurrency;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="currency", cascade = CascadeType.ALL)
    @Fetch(value= FetchMode.SELECT)
    private List<Rate> rates;
}
