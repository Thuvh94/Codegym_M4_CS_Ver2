package com.codegym.case4.service.Book;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

public interface IBookService extends IGeneralService<Book> {
    Page<Book> findAllByTitleContainingAndDeletedIsFalse(String title, Pageable pageable);
    Page<Book> findAllByCategories(Long categoryId, Pageable pageable);
    Page<Book> findAllByAuthorId(Long id, Pageable pageable);
    Page<Book> findAllByIsDeletedTrue(Pageable pageable);
    Page<Book> findAllByTitleContainingAndDeletedIsTrue(String title, Pageable pageable);
    void restore(Long bookId);
}
