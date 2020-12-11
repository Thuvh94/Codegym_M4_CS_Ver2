package com.codegym.case4.service.User;

import com.codegym.case4.model.User;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGeneralService<User> {
    Page<User> findAllByNameContaining(String name, Pageable pageable);
}
