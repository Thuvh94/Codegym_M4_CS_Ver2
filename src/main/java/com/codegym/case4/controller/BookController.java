package com.codegym.case4.controller;

import com.codegym.case4.model.Author;
import com.codegym.case4.model.Book;
import com.codegym.case4.model.BookForm;
import com.codegym.case4.model.Category;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
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

    // Create book
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new BookForm());
        return modelAndView;
    }

//    @PostMapping("/create")
//    public ModelAndView saveBook(@Validated @ModelAttribute("book") Book book, BindingResult bindingResult){
//        if(bindingResult.hasFieldErrors()){
//            ModelAndView modelAndView = new ModelAndView("/book/create");
//            return modelAndView;
//        }
//        bookService.save(book);
//        ModelAndView modelAndView = new ModelAndView("/book/create");
//        modelAndView.addObject("book", new Book());
//        modelAndView.addObject("message", "New book is created successfully");
//        return modelAndView;
    @PostMapping("/create")
    public RedirectView saveBook(@ModelAttribute BookForm bookForm){
        Book book = new Book(bookForm.getBookId(),bookForm.getTitle(),bookForm.getDescription(),bookForm.isDeleted(),
                bookForm.getPublishedDate(), bookForm.getPages(), bookForm.getCategories(),bookForm.getAuthorId());
        MultipartFile multipartFile = bookForm.getCoverImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(bookForm.getCoverImg().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        book.setCoverImg(fileName);
        bookService.save(book);
        return new RedirectView("/book");
    }
//        Product product1 = new Product.ProductBuilder(product.getName())
//                .description(product.getDescription()).build();
//        MultipartFile multipartFile = product.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(product.getImage().getBytes(), new File(this.fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        product1.setImage(fileName);
//        productService.save(product1);
//        return new RedirectView("");
//    }
//    }

    // Delete function
    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Book> deletedBook = bookService.findById(id);
        if(deletedBook!=null){
            Book book = deletedBook.get();
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book",book);
            return modelAndView;
        }
        else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteCustomer(@ModelAttribute("book") Book book){
        bookService.remove(book.getBookId());
        return "redirect:list";
    }

    // Edit function
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Book> editedBook = bookService.findById(id);
        if(editedBook != null) {
            Book book = editedBook.get();
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("selectedCategories",book.getCategories());
            modelAndView.addObject("book", book);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit")
    public ModelAndView updateBook(@ModelAttribute("book") Book book){
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("book", book);
        modelAndView.addObject("message", "Book updated successfully");
        return modelAndView;
    }
}
