package com.codegym.case4.repository;

import com.codegym.case4.model.User;
import com.codegym.case4.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserPrincipalRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
    User findOneByUserId(Long id);

}
