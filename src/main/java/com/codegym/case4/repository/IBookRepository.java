package com.codegym.case4.repository;

import com.codegym.case4.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends PagingAndSortingRepository<Book,Long> {
    Page<Book> findAllByIsDeletedFalse(Pageable pageable);
    Page<Book> findAllByTitleContainingAndDeletedIsFalse(String title, Pageable pageable);
}
