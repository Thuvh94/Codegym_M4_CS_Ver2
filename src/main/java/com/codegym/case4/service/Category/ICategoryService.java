package com.codegym.case4.service.Category;

import com.codegym.case4.model.Category;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ICategoryService{
    Iterable<Category> findAll();

    Category save(Category category);

    Optional<Category> findById(Long id);

    void remove(Long id);
}
