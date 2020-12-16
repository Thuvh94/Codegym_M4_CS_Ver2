package com.codegym.case4.service.Author;

import com.codegym.case4.model.Author;
import com.codegym.case4.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AuthorServiceImpl implements IAuthorService{
    @Autowired
    private IAuthorRepository authorRepository;

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Long> findAllAuthorHaveBook() {
        return  authorRepository.findAllAuthorHaveBook();
    }
}
