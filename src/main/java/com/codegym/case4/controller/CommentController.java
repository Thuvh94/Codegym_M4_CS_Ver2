package com.codegym.case4.controller;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.Comment;
import com.codegym.case4.model.CommentForm;
import com.codegym.case4.model.User;
import com.codegym.case4.service.Book.IBookService;
import com.codegym.case4.service.Comment.ICommentService;
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
public class CommentController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/commentCreate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Comment createComment(@RequestBody CommentForm commentForm){
        User user= userService.findById(commentForm.getUser()).get();
        Book book = bookService.findById(commentForm.getBook()).get();
        Comment comment = new Comment(null,user,book, commentForm.getContent());
        return commentService.save(comment);
    }

    @RequestMapping(value = "/commentList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<Comment> allComment(@PageableDefault(size = 10) Pageable pageable){
        return commentService.findAll(pageable);
    }

//    @RequestMapping(value = "/commentByBook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public Page<Comment> allComment(@PageableDefault(size = 10) Pageable pageable){
//        return commentService.findAll(pageable);
//    }

}
