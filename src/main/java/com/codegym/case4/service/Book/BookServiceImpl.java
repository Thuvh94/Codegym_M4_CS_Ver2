package com.codegym.case4.service.Book;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;
import com.codegym.case4.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private IBookRepository bookRepository;

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        bookRepository.remove(id);
    }

    @Override
    public Page<Book> findAllByTitleContainingAndDeletedIsFalse(String title, Pageable pageable) {
        return bookRepository.findAllByTitleContaining(title,pageable);
    }

    @Override
    public Page<Book> findAllByCategories(Long categoryId, Pageable pageable) {
        return bookRepository.findAllByCategories(categoryId,pageable);
    }

    @Override
    public Page<Book> findAllByAuthorId(Long id, Pageable pageable) {
        return bookRepository.findAllByAuthorId(id, pageable);
    }
}
