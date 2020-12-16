package com.codegym.case4.controller;

import com.codegym.case4.model.Author;
import com.codegym.case4.model.Category;
import com.codegym.case4.model.Role;
import com.codegym.case4.model.User;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Category.ICategoryService;
import com.codegym.case4.service.Role.IRoleService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("allRoles")
    private Iterable<Role> allRoles(){
        return roleService.findAll();
    }

    @ModelAttribute("allUsers")
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping
    public ModelAndView listUsers(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<User> users;
        if (s.isPresent()) {
            users = userService.findAllByNameContaining(s.get(), pageable);
        } else {
            users = userService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    // Create user
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/user/create");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveUser(@Validated @ModelAttribute("user") User user, BindingResult bindingResult  ){
        if(bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("/user/create");
            return modelAndView;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles().isEmpty())
            user.setRoles(defaultRole());
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/user/create");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "New user is created successfully");
        return modelAndView;
    }

    // Delete function
    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<User> deletedUser = userService.findById(id);
        if(deletedUser!=null){
            User user = deletedUser.get();
            ModelAndView modelAndView = new ModelAndView("/user/delete");
            modelAndView.addObject("user",user);
            return modelAndView;
        }
        else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user){
        userService.remove(user.getUserId());
        return "redirect:/admin/user";
    }

    // Edit function
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<User> editedUser = userService.findById(id);
        if(editedUser != null) {
            User user = editedUser.get();
            ModelAndView modelAndView = new ModelAndView("/user/edit");
            modelAndView.addObject("user", user);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit")
    public ModelAndView updateUser(@Validated @ModelAttribute("user") User user,BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            showEditForm(user.getUserId());
        }
        if(user.getRoles().isEmpty())
            user.setRoles(defaultRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/user/edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "User updated successfully");
        return modelAndView;
    }

    public Set<Role> defaultRole(){
        Set<Role> roleSet = new HashSet<Role>();
        roleSet.add(roleService.findById(1L).get());
        return roleSet;
    }

}
