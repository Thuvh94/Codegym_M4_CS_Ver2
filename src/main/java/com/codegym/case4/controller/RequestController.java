package com.codegym.case4.controller;

import com.codegym.case4.model.Author;
import com.codegym.case4.model.User;
import com.codegym.case4.model.UserPrincipal;
import com.codegym.case4.service.User.IUserService;
import com.codegym.case4.service.UserPrincipal.IUserPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client/request")
public class RequestController {
//    @Autowired
//    IUserPrincipalService userPrincipalService;
    @Autowired
    IUserService userService;

    @GetMapping
    public ModelAndView example() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userPrincipalService.findByUserName(userPrincipal.getUsername());
        User user = userService.findByUsername(userPrincipal.getUsername());
        System.out.println(user);
        ModelAndView modelAndView = new ModelAndView("/request/example");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
