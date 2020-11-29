package com.currencyexchange.services;

import com.currencyexchange.entities.Currency;
import com.currencyexchange.entities.CurrencyExchange;
import com.currencyexchange.entities.Rate;
import com.currencyexchange.repositories.CurrencyExchangeRepository;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final CurrencyRepository currencyRepository;
    private final RateRepository rateRepository;
    private final RateService rateService;

    @Autowired
    CurrencyExchangeServiceImpl(List<CurrencyExchangeRepository> currencyExchangeRepository, CurrencyRepository currencyRepository, RateRepository rateRepository, RateService rateService) {
        this.repositories = currencyExchangeRepository;
        this.currencyRepository = currencyRepository;
        this.rateRepository = rateRepository;
        this.rateService = rateService;
    }

    @Override
    public CurrencyExchange convert(String sourceCurrency, String targetCurrency, Double amount) {

        NumberValue factor = this.repositories.get(0).calculate(sourceCurrency, targetCurrency);
        Double convertedAmount = Double.parseDouble(factor.toString()) * amount;

        return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount, Double.parseDouble(factor.toString()), LocalDate.now());
    }
    // Tutaj transactional i pobieramy z bazy/wrzucamy do bazy
    @Transactional//(isolation = )
    @Override
    public CurrencyExchange monetaryConvert(String sourceCurrency, String targetCurrency, Double amount) {

        // tu powinnismy zwracac rate + data
        Currency validCurrency = currencyRepository.findByCurrencies(sourceCurrency, targetCurrency);

        try{
            if(validCurrency == null){
                throw new Exception("Invalid currencies!");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // nazewnictwo
        List<Rate> ratesList = validCurrency.getRates();

        if(!ratesList.isEmpty()){

            Rate rate = rateService.fetchRate(ratesList);

            if(rate != null){
                return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, rate.getRate() * amount, rate.getRate(), LocalDate.now());
            }
        }

        NumberValue factor = this.repositories.get(1).calculate(sourceCurrency, targetCurrency);
        Double convertedAmount = Double.parseDouble(factor.toString()) * amount;

        // TO DO: Fix this timeout - API related issue
        // rateRepository.save(new Rate(sourceCurrency, targetCurrency, Double.parseDouble(factor.toString()), LocalDate.now()));

        rateRepository.insertRate(3, LocalDate.now().toString(), Double.parseDouble(factor.toString()), sourceCurrency, targetCurrency, 1);

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
        return this.repositories.stream().filter(repository -> ClassUtils.getUserClass(repository).equals(clazz)).findFirst().map(repository -> {
            NumberValue factor = repository.calculate(sourceCurrency, targetCurrency);
            Double convertedAmount = Double.parseDouble(factor.toString()) * amount;
            return new CurrencyExchange(0, sourceCurrency, targetCurrency, amount, convertedAmount, Double.parseDouble(factor.toString()), LocalDate.now());
        }).orElse(null);
    }
}
