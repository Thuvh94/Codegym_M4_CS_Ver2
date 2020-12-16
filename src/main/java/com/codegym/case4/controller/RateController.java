package com.codegym.case4.controller;

import com.codegym.case4.model.*;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Comment.ICommentService;
import com.codegym.case4.service.Rate.IRateService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class RateController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRateService rateService;

    @RequestMapping(value = "/rateCreate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Rate rateComment(@RequestBody RateForm rateForm){
        User user= userService.findById(rateForm.getUserId()).get();
        Book book = bookService.findById(rateForm.getBookId()).get();
//        Rate rate = new Rate(null,book,user,rateForm.getRate());
        Rate rate = new Rate(book,user,rateForm.getRate());
        return rateService.save(rate);
    }

//    @RequestMapping(value = "/rateAverage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public Page<Comment> allComment(@PageableDefault(size = 10) Pageable pageable){
//        return commentService.findAll(pageable);
//    }

}
