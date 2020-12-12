package com.codegym.case4.service.Role;

import com.codegym.case4.model.Role;

import java.util.Optional;

public interface IRoleService {
    Iterable<Role> findAll();

    Role save(Role t);

    Optional<Role> findById(Long id);

    void remove(Long id);

    Role findRoleByRoleName(String roleName);
}
