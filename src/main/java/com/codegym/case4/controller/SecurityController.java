package com.codegym.case4.controller;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;
import com.codegym.case4.model.Role;
import com.codegym.case4.model.User;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Category.ICategoryService;
import com.codegym.case4.service.Role.IRoleService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class SecurityController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @RequestMapping("/")
    public ModelAndView listBooks(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByTitleContainingAndDeletedIsFalse(s.get(), pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/homeClient");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/client")
    public ModelAndView listBooksC(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByTitleContainingAndDeletedIsFalse(s.get(), pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/homeClient");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/admin")
    public String adminPage() {
        return "redirect:/admin/book";

    }
    @GetMapping("/create")
    public ModelAndView Signup() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("create");
        if (!bindingResult.hasFieldErrors()) {
            Role role = roleService.findRoleByRoleName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            modelAndView.addObject("user", new User());
            modelAndView.addObject("message", "New user created successfully");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

}
