package com.codegym.case4;

import com.codegym.case4.model.Role;
import com.codegym.case4.service.Role.IRoleService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class Case4Application {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    public void init(){
        List<Role> roles = (List<Role>) roleService.findAll();
        if(roles.isEmpty()){
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setRoleName("ROLE_USER");
            roleService.save(roleUser);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(Case4Application.class, args);
    }

}
