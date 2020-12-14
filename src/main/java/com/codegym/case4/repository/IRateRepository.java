package com.codegym.case4.repository;
import com.codegym.case4.model.Book;
import com.codegym.case4.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRateRepository extends JpaRepository<Rate,Long> {
    @Query(value = "select * from rates rates where rates.bookId =:id",nativeQuery = true)
    List<Rate> findRatesByBookId(@Param("id") Long bookId);

    @Query(value="select AVG(rate) from rates rates where rates.bookId =:id",nativeQuery = true)
    Float averageRates(@Param("id") Long bookId);
}
