package com.codegym.case4.repository;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ICategoryRepository extends PagingAndSortingRepository<Category,Long> {
}
