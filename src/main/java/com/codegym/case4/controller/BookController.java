package com.codegym.case4.controller;

import com.codegym.case4.model.Author;
import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IAuthorService authorService;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("allAuthors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @GetMapping("/list")
    public ModelAndView listBooks(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByTitleContainingAndDeletedIsFalse(s.get(), pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    // Create book
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

//    @PostMapping("/create-customer")
//    public ModelAndView saveCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult){
//        if(bindingResult.hasFieldErrors()){
//            ModelAndView modelAndView = new ModelAndView("/customer/create");
//            return modelAndView;
//        }
//        customerService.save(customer);
//        ModelAndView modelAndView = new ModelAndView("/customer/create");
//        modelAndView.addObject("customer", new Customer());
//        modelAndView.addObject("message", "New customer created successfully");
//        return modelAndView;
//    }

}
