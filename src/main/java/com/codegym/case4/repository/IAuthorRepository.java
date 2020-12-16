package com.codegym.case4.repository;

import com.codegym.case4.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthorRepository extends PagingAndSortingRepository<Author, Long> {
    @Query(value = "select authorId from books books group by authorId",nativeQuery = true)
    List<Long> findAllAuthorHaveBook();
}
