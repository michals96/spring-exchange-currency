package com.currencyexchange.services;

import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.repositories.CurrencyExchangeRepository;
import com.currencyexchange.repositories.JavaCurrencyExchangeApiRepository;
import com.currencyexchange.repositories.JavaCurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.money.NumberValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("currencyExchangeService")
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final List<CurrencyExchangeRepository> currencyExchangeRepository;

    @Autowired
    CurrencyExchangeServiceImpl( List<CurrencyExchangeRepository> currencyExchangeRepository)
    {
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @Override
    public CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount)  {

        NumberValue factor = this.currencyExchangeRepository.get(0).calculate(sourceCurrency, targetCurrency);
        Double convertedAmount = Double.parseDouble(factor.toString()) * amount;

        return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
    }

    @Override
    public Map<String, CurrencyExchange> convertWithAllApi(String sourceCurrency, String targetCurrency, Double amount) {

        return this.currencyExchangeRepository.stream().map(repository -> {
            NumberValue factor = repository.calculate(sourceCurrency, targetCurrency);
            Double convertedAmount = Double.parseDouble(factor.toString()) * amount;
            String repositoryStr = repository.toString();
            CurrencyExchange currencyExchange = new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount);
            List myList = new ArrayList();
            myList.add(repositoryStr);
            myList.add(currencyExchange);
            return myList;
        }).collect(Collectors.toMap(list -> (String)list.get(0), list -> (CurrencyExchange)list.get(1)));
    }

    @Override
    public CurrencyExchange convertWithApi(String sourceCurrency, String targetCurrency, Double amount, Class<?> clazz) {

        Map<String, CurrencyExchange> servicesMap = convertWithAllApi(sourceCurrency, targetCurrency, amount);
        String className = clazz.getSimpleName();
        
        final String repositoryServiceName = JavaCurrencyExchangeRepository.class.getSimpleName();
        final String apiRepositoryServiceName = JavaCurrencyExchangeApiRepository.class.getSimpleName();

        if(className.equals(repositoryServiceName))
        {
            return (CurrencyExchange) servicesMap.values().toArray()[0];
        }
        else if(className.equals(apiRepositoryServiceName))
        {
            return (CurrencyExchange) servicesMap.values().toArray()[1];
        }
        else return null;
    }
}
