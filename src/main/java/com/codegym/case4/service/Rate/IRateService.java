package com.codegym.case4.service.Rate;

import com.codegym.case4.model.Category;
import com.codegym.case4.model.Rate;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRateService {
    List<Rate> findRatesByBookId(Long bookId);

    Rate save(Rate rate);

    Float averageRates(Long bookId);

    Optional<Rate> findRateByUserAndBook(Long bookId, Long userId );
//
//    void remove(Long id);
}
