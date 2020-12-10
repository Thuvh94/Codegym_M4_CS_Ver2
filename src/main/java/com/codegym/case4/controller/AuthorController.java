package com.codegym.case4.controller;

import com.codegym.case4.model.Author;
import com.codegym.case4.service.Author.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private IAuthorService iAuthorService;


    @GetMapping
    public ModelAndView listAuthor(@PageableDefault(size = 10) Pageable pageable) {
        Page<Author> authors = iAuthorService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("author/list");
        modelAndView.addObject("authors", authors);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("author/create");
        modelAndView.addObject("author", new Author());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createAuthor(Author author) {
        iAuthorService.save(author);
        ModelAndView modelAndView = new ModelAndView("author/create", "author", new Author());
        modelAndView.addObject("message", "new author created successfully");
        return modelAndView;
    }

//    @GetMapping("/view-author/{id}")
//    public ModelAndView viewAuthor(@PathVariable("id") Long id) {
//        Optional<Author> author = iAuthorService.findById(id);
//        if (author == null) {
//            return new ModelAndView("author/list");
//        }
//
//        Iterable<Book> book = myBookService.findAllByAuthor(author.get());
//
//        ModelAndView modelAndView = new ModelAndView("/author/view");
//        modelAndView.addObject("author", author);
//        modelAndView.addObject("books", book);
//        return modelAndView;
//    }

    @GetMapping("/{id}/edit")
    public ModelAndView editAuthor(@PathVariable Long id) {
        Optional<Author> author = iAuthorService.findById(id);
        ModelAndView modelAndView = new ModelAndView("author/edit");
        if (author != null) {
            modelAndView.addObject("author", author);
        } else {
            modelAndView.addObject("message", "unknown author ");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView updateAuthor(@ModelAttribute("author") Author author) {
        iAuthorService.save(author);
        ModelAndView modelAndView = new ModelAndView("/author/edit");
        modelAndView.addObject("author", author);
        modelAndView.addObject("message", " author updated successfully");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteAuthor(@PathVariable Long id,@PageableDefault(size = 10) Pageable pageable) {
        iAuthorService.remove(id);
        Page<Author> authors = iAuthorService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("author/list");
        modelAndView.addObject("authors", authors);
        return modelAndView;

    }
}
