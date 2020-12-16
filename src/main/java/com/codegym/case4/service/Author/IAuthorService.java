package com.codegym.case4.service.Author;

import com.codegym.case4.model.Author;
import com.codegym.case4.service.IGeneralService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IAuthorService extends IGeneralService<Author> {
    List<Long> findAllAuthorHaveBook();
}
