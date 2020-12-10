package com.codegym.case4.service.Book;

import com.codegym.case4.model.Book;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface IBookService extends IGeneralService<Book> {
    Page<Book> findAllByTitleContainingAndDeletedIsFalse(String title, Pageable pageable);
}
