package com.codegym.case4.controller;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Category;
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

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @Autowired
    private IBookService bookService;

    @GetMapping("/admin/category")
    public ModelAndView listCategory() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("categorys", categories);
        return modelAndView;
    }

    @GetMapping("/admin/category/create")
    public ModelAndView showCreateCategory() {
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/admin/category/create")
    public ModelAndView createCategory(@Validated @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("category/create");
            return modelAndView;
        }
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("category/create", "category", new Category());
        modelAndView.addObject("message", "New category created successfully");
        return modelAndView;
    }

    @GetMapping("/admin/category/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView("category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/admin/category/edit")
    public ModelAndView updateCategory(@Validated @ModelAttribute("category") Category category,BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return showEditForm(category.getCategoryId());
        }
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("category/edit");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "Updated Category successful!");
        return modelAndView;
    }

    @GetMapping("/admin/category/delete/{id}")
    public ModelAndView deleteAuthor(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("category/list");
        List<Long> categoriesHaveBook = categoryService.findAllCategoriesHaveBook();
        if(categoriesHaveBook.contains(id)){
            modelAndView.addObject("alert","Operation not permitted!");
        }
        else {
            categoryService.remove(id);
        }
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categorys", categories);
        return modelAndView;
    }

    @GetMapping("/admin/category/{id}")
    public ModelAndView findBooksByCategoryId(@PathVariable Long id,@PageableDefault(size = 10) Pageable pageable){
        Category category = categoryService.findById(id).get();
        Page<Book> books =  bookService.findAllByCategories(id,pageable);
        ModelAndView modelAndView = new ModelAndView("/category/detail");
        modelAndView.addObject("books",books);
        modelAndView.addObject("category",category);
        return  modelAndView;
    }

    @GetMapping("/client/category/{id}")
    public ModelAndView findBooksByCategoryIdC(@PathVariable Long id,@PageableDefault(size = 10) Pageable pageable){
        Category category = categoryService.findById(id).get();
        Page<Book> books =  bookService.findAllByCategories(id,pageable);
        ModelAndView modelAndView = new ModelAndView("/category/detailClient");
        modelAndView.addObject("books",books);
        modelAndView.addObject("category",category);
        return  modelAndView;
    }


}

