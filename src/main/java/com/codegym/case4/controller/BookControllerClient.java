package com.codegym.case4.controller;

import com.codegym.case4.model.*;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Category.ICategoryService;
import com.codegym.case4.service.Comment.ICommentService;
import com.codegym.case4.service.Rate.IRateService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client/book")
public class BookControllerClient {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IRateService rateService;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("allAuthors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @Autowired
    private IAuthorService iAuthorService;


    @GetMapping("/viewDetail/{id}")
    public ModelAndView viewBookDetail(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable) {
        Optional<Book> viewBook = bookService.findById(id);
        if (viewBook != null) {
            ModelAndView modelAndView = new ModelAndView("/book/bookDetailClient");

            Page<Book> books = bookService.findAllByAuthorId(id, pageable);
            modelAndView.addObject("books", books);
            Book book = viewBook.get();
            modelAndView.addObject("book", book);
            modelAndView.addObject("user", getCurrentUser());
            modelAndView.addObject("average",rateService.averageRates(id));
            modelAndView.addObject("totalRate",rateService.findRatesByBookId(id).size());
            modelAndView.addObject("comment", new CommentForm());
            modelAndView.addObject("rate", new RateForm());
            Page<Comment> allComment = commentService.findCommentByBookId(id, pageable);
            modelAndView.addObject("allComment", allComment);
            modelAndView.addObject("totalComment",allComment.getTotalElements());

            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername());
        return user;
    }


}
