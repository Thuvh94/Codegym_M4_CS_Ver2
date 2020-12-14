package com.codegym.case4.controller;

import com.codegym.case4.model.Author;
import com.codegym.case4.model.Book;
import com.codegym.case4.model.BookForm;
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



    @GetMapping("/view/{id}")
    public ModelAndView showEditForm(@PathVariable Long id,@PageableDefault(size = 10) Pageable pageable){
        Optional<Book> viewsBook = bookService.findById(id);
        if(viewsBook != null) {
            Book book = viewsBook.get();
            BookForm bookForm = new BookForm(book.getBookId(),null,book.getTitle(),book.getDescription(),book.isDeleted(),
                    book.getPublishedDate(),book.getPages(),book.getCategories(),book.getAuthorId());
            Page<Book> books = bookService.findAllByAuthorId(id, pageable);
            ModelAndView modelAndView = new ModelAndView("/book/bookDetailClient");
            modelAndView.addObject("books",books);
            modelAndView.addObject("selectedCategories",book.getCategories());
            modelAndView.addObject("coverImgLink",book.getCoverImg());
            modelAndView.addObject("book", bookForm);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
}
