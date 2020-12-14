package com.codegym.case4.service.Rate;

import com.codegym.case4.model.Category;
import com.codegym.case4.model.Rate;

import java.util.List;
import java.util.Optional;

public interface IRateService {
    List<Rate> findRatesByBookId(Long bookId);

    Rate save(Rate rate);

    Float averageRates(List<Rate> rates);

//    Optional<Rate> findById(Long id);
//
//    void remove(Long id);
}
