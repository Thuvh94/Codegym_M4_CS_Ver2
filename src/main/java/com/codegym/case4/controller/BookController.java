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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/create")
    public ModelAndView saveCustomer(@Validated @ModelAttribute("book") Book book, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("/book/create");
            return modelAndView;
        }
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "New book is created successfully");
        return modelAndView;
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
    }

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


}
