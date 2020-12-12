package com.codegym.case4.service.User;

import com.codegym.case4.model.User;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Page<User> findAllByNameContaining(String name, Pageable pageable);
    User findByUsername(String username);
    User findByUserNameAndIsDeletedIsFalse(String userName);
}
