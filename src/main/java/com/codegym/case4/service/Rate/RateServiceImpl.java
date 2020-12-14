package com.codegym.case4.service.Rate;

import com.codegym.case4.model.Rate;
import com.codegym.case4.repository.IRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RateServiceImpl implements IRateService{
    @Autowired
    IRateRepository rateRepository;

    @Override
    public List<Rate> findRatesByBookId(Long bookId) {
        return rateRepository.findRatesByBookId(bookId);
    }

    @Override
    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Float averageRates(List<Rate> rates) {
        float sum = 0;
        for (int i = 0; i < rates.size(); i++) {
            sum+=rates.get(i).getRate();
        }
        System.out.println();
        float average = sum/rates.size();
        System.out.println(average);
        return average;
    }
}
