package com.codegym.case4.repository;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ICategoryRepository extends PagingAndSortingRepository<Category,Long> {
    @Query(value= "SELECT categories from books_categories group by categories",nativeQuery = true)
    List<Long> findAllCategoriesHaveBook();


}
