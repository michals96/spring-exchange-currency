package com.currencyexchange.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String firstName, lastName;
}


/*
   1. sprawdzam baze czy obiekt istnieje (po dacie - danego dnia i z jakiej na jaką walutę)
   2. jeśli nie istnieje to wyślij zapytanie do API i włóż do bazy
   3. w bazie ląduje data, z jakiej waluty na jaką i kurs
*/