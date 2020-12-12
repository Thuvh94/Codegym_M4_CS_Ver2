package com.codegym.case4.repository;

import com.codegym.case4.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository  extends PagingAndSortingRepository<Role,Long> {
    Role findRoleByRoleName(String roleName);
}
