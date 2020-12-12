package com.codegym.case4.service.UserPrincipal;

import com.codegym.case4.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserPrincipalService extends UserDetailsService {
    User findByUserName(String username);
}
