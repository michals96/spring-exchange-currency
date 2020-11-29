package com.currencyexchange.services;

import com.currencyexchange.entities.Rate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("rateService")
public class RateServiceImpl implements RateService{

    @Override
    public Rate fetchRate(List<Rate> ratesList) {
        for(Rate rate: ratesList){
            if(rate.getDate().toString().equals(LocalDate.now().toString())){
                return rate;
            }
        }
        return null;
    }

}
