package com.codegym.case4.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface IGeneralService<T> {
    Page<T> findAll(Pageable pageable);

    T save(T t);

    Optional<T> findById(Long id);

    void remove(Long id);
}
