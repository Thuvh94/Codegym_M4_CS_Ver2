package com.codegym.case4.controller;

import com.codegym.case4.model.*;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Category.ICategoryService;
import com.codegym.case4.service.Comment.ICommentService;
import com.codegym.case4.service.Rate.IRateService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/book")
public class BookController {
    private final String DEFAULT_IMG = "defaultCoverImg.png";
    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IRateService rateService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommentService commentService;

    @Value("${upload.path}")
    private String fileUpload;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("allAuthors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorService.findAll(pageable);
    }

    // List book
    @GetMapping
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
// View deleted book
    @GetMapping("/deletedBook")
    public ModelAndView listDeletedBooks(@RequestParam("s") Optional<String> s,@PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByTitleContainingAndDeletedIsTrue(s.get(),pageable);
        } else {
            books = bookService.findAllByIsDeletedTrue(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/deletedList");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    //Restore book
    @GetMapping("/restore/{id}")
    public ModelAndView restoreBook(@RequestParam("s") Optional<String> s,@PathVariable Long id,@PageableDefault(size = 10) Pageable pageable){
        bookService.restore(id);
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByTitleContainingAndDeletedIsTrue(s.get(),pageable);
        } else {
            books = bookService.findAllByIsDeletedTrue(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/deletedList");
        modelAndView.addObject("message","Book is restored successfully");
        modelAndView.addObject("books",books);
        return modelAndView;
    }

    // Create book
    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new BookForm());
        modelAndView.addObject("author",new Author());
        return modelAndView;
    }

@PostMapping("/create")
    public ModelAndView saveBook(@Validated @ModelAttribute("book") BookForm bookForm,BindingResult bindingResult,@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        if (bindingResult.hasFieldErrors()) {
        ModelAndView modelAndView = new ModelAndView("/book/create");
            return modelAndView;
        }
        Book book = new Book(bookForm.getBookId(), bookForm.getTitle(), bookForm.getDescription(), bookForm.isDeleted(),
                bookForm.getPublishedDate(), bookForm.getPages(), bookForm.getCategories(), bookForm.getAuthorId());
        MultipartFile multipartFile = bookForm.getCoverImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(bookForm.getCoverImg().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileName.equals(""))
            book.setCoverImg(DEFAULT_IMG);
        else
            book.setCoverImg(fileName);
        bookService.save(book);
        return listBooks(s,pageable);
    }

    // Delete function
    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Book> deletedBook = bookService.findById(id);
        if (deletedBook != null) {
            Book book = deletedBook.get();
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteBook(@ModelAttribute("book") Book book) {
        bookService.remove(book.getBookId());
        return "redirect:/admin/book";
    }

    //     Edit function
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Book> editedBook = bookService.findById(id);
        if (editedBook != null) {
            Book book = editedBook.get();
            BookForm bookForm = new BookForm(book.getBookId(), null, book.getTitle(), book.getDescription(), book.isDeleted(),
                    book.getPublishedDate(), book.getPages(), book.getCategories(), book.getAuthorId());
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("selectedCategories", book.getCategories());
            modelAndView.addObject("coverImgLink", book.getCoverImg());
            modelAndView.addObject("book", bookForm);
            modelAndView.addObject("author",new Author());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateBook(@Validated @ModelAttribute("book") BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
           showEditForm(bookForm.getBookId());
        }
        MultipartFile multipartFile = bookForm.getCoverImg();
        String fileName = multipartFile.getOriginalFilename();
        Book editedBook = new Book(bookForm.getBookId(), fileName, bookForm.getTitle(), bookForm.getDescription(), bookForm.isDeleted(),
                bookForm.getPublishedDate(), bookForm.getPages(), bookForm.getCategories(), bookForm.getAuthorId());
        if (fileName.equals("")) {
            Book book1 = bookService.findById(bookForm.getBookId()).get();
            fileName = book1.getCoverImg();
            editedBook.setCoverImg(fileName);
        } else {
            try {
                FileCopyUtils.copy(bookForm.getCoverImg().getBytes(), new File(this.fileUpload + fileName));
                editedBook.setCoverImg(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bookService.save(editedBook);
        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("coverImgLink", fileName);
        modelAndView.addObject("message", "Book updated successfully");
        modelAndView.addObject("book", bookForm);
        return modelAndView;
    }

    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername());
        return user;
    }
}
