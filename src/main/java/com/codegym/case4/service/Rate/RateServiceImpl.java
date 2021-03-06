package com.codegym.case4.service.Rate;

import com.codegym.case4.model.Rate;
import com.codegym.case4.repository.IRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Rate> checkRate = findRateByUserAndBook(rate.getBookId().getBookId(),rate.getUserId().getUserId());
        if(checkRate.isPresent()){
            rate.setId(checkRate.get().getId());
        }
        return rateRepository.save(rate);
    }

    @Override
    public Float averageRates(Long bookId) {
        return rateRepository.averageRates(bookId);
    }

    @Override
    public Optional<Rate> findRateByUserAndBook(Long bookId, Long userId) {
        return rateRepository.findRateByUserAndBook(bookId,userId);
    }
}
