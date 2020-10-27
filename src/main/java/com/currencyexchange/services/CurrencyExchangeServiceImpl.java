package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.repositories.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.money.NumberValue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // The code below is to hack servlet exception while refreshing the page.
        // Double convertedAmount = Double.parseDouble("1.0") * amount;

        return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount, Double.parseDouble(factor.toString()), LocalDate.now());
    }

    @Override
    public Map<String, CurrencyExchange> convertWithAllApi(String sourceCurrency, String targetCurrency, Double amount) {

        return this.repositories.stream().map(repository -> {
            NumberValue factor = repository.calculate(sourceCurrency, targetCurrency);
            Double convertedAmount = Double.parseDouble(factor.toString()) * amount;
            String repositoryStr = repository.toString();
            CurrencyExchange currencyExchange = new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount, Double.parseDouble(factor.toString()), LocalDate.now());
            List myList = new ArrayList();
            myList.add(repositoryStr);
            myList.add(currencyExchange);
            return myList;
        }).collect(Collectors.toMap(list -> (String) list.get(0), list -> (CurrencyExchange) list.get(1)));
    }

    @Override
    public CurrencyExchange convertWithApi(String sourceCurrency, String targetCurrency, Double amount, Class<?> clazz) {

        // For some reason repositories got wrapped into proxy for instance getClass().getSimpleName() got us
        // JavaCurrencyExchangeApiRepository$$EnhancerBySpringCGLIB$$96b10576 instead of JavaCurrencyExchangeApiRepository
        // ClassUtils.getUserClass() got the problem solved
        return this.repositories.stream().filter(repository -> ClassUtils.getUserClass(repository).equals(clazz)).findFirst().map(repository -> {
            NumberValue factor = repository.calculate(sourceCurrency, targetCurrency);
            Double convertedAmount = Double.parseDouble(factor.toString()) * amount;
            return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount, Double.parseDouble(factor.toString()), LocalDate.now());
        }).orElse(null);
    }
}
