package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.repositories.CurrencyExchangeRepository;
import com.currencyexchange.repositories.JavaCurrencyExchangeApiRepository;
import com.currencyexchange.repositories.JavaCurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.NumberValue;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("currencyExchangeService")
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final List<CurrencyExchangeRepository> repositories;

    @Autowired
    CurrencyExchangeServiceImpl(List<CurrencyExchangeRepository> currencyExchangeRepository) {
        this.repositories = currencyExchangeRepository;
    }

    @Override
    public CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount) {

        NumberValue factor = this.repositories.get(0).calculate(sourceCurrency, targetCurrency);
        Double convertedAmount = Double.parseDouble(factor.toString()) * amount;

        return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
    }

    @Override
    public Map<String, CurrencyExchange> convertWithAllApi(String sourceCurrency, String targetCurrency, Double amount) {

        return this.repositories.stream().map(repository -> {
            NumberValue factor = repository.calculate(sourceCurrency, targetCurrency);
            Double convertedAmount = Double.parseDouble(factor.toString()) * amount;
            String repositoryStr = repository.toString();
            CurrencyExchange currencyExchange = new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
            List myList = new ArrayList();
            myList.add(repositoryStr);
            myList.add(currencyExchange);
            return myList;
        }).collect(Collectors.toMap(list -> (String) list.get(0), list -> (CurrencyExchange) list.get(1)));
    }

    @Override
    public CurrencyExchange convertWithApi(String sourceCurrency, String targetCurrency, Double amount, Class<?> clazz) {

        /* to practice: Monads, Spring databases, Optionals
        Optional<String> name = Optional.of("Hello world");
        Stream<String> stream = name.stream();

        Stream<Optional<String>> stringOptional = Stream.of(name);

        Stream<Object> objectStream = stringOptional.flatMap(name -> {
            return null;
        });*/

        return this.repositories.stream().filter(repository -> repository.getClass().equals(clazz)).findFirst().map(repository -> {
            NumberValue factor = repository.calculate(sourceCurrency, targetCurrency);
            Double convertedAmount = Double.parseDouble(factor.toString()) * amount;
            return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
        }).orElse(null);
    }
}
