package com.codegym.case4.repository;

import com.codegym.case4.model.Author;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
