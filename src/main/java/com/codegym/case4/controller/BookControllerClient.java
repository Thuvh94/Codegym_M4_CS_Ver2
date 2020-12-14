package com.codegym.case4.controller;

import com.codegym.case4.model.*;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Category.ICategoryService;
import com.codegym.case4.service.Comment.ICommentService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
            Book book = viewBook.get();
//            BookForm bookForm = new BookForm(book.getBookId(),null,book.getTitle(),book.getDescription(),book.isDeleted(),
//                    book.getPublishedDate(),book.getPages(),book.getCategories(),book.getAuthorId());
            ModelAndView modelAndView = new ModelAndView("/book/bookDetailClient");
            Page<Book> books = bookService.findAllByAuthorId(id, pageable);
            modelAndView.addObject("books", books);
//            modelAndView.addObject("selectedCategories",book.getCategories());
//            modelAndView.addObject("coverImgLink",book.getCoverImg());
            modelAndView.addObject("book", book);
            modelAndView.addObject("user", getCurrentUser());
            modelAndView.addObject("comment", new CommentForm());
            Page<Comment> allComment = commentService.findCommentByBookId(id, pageable);
            modelAndView.addObject("allComment", allComment);

            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    //        public ModelAndView showCommentForm(@PageableDefault(size = 10) Pageable pageable){
//        ModelAndView modelAndView = new ModelAndView("/book/demoComment");
//        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userService.findByUsername(userPrincipal.getUsername());
//        Book book = bookService.findById(1L).get();
//        Page<Comment> allComment = commentService.findCommentByBookId(1L, pageable);
//        modelAndView.addObject("allComment",allComment);
//        modelAndView.addObject("comment",new CommentForm());
//        modelAndView.addObject("user",user);
//        modelAndView.addObject("book",book);
//
//        return modelAndView;
//    }
    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername());
        return user;
    }
}
