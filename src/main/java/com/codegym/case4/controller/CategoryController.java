package com.codegym.case4.controller;

import com.codegym.case4.model.Category;
import com.codegym.case4.service.Category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ModelAndView listCategory() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("categorys", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateCategory() {
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createCategory(Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("category/create", "category", new Category());
        modelAndView.addObject("message", "new category created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView("category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView updateCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("category/edit");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "Updated Category successful!!!");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteAuthor(@PathVariable Long id) {
        categoryService.remove(id);
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("categorys", categories);
        return modelAndView;
    }

}

