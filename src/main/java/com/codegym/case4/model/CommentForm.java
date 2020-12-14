package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Data
public class CommentForm {
    private Long commentId;
    private Long user;
    private Long book;
    private String content;

    public CommentForm() {
    }

    public CommentForm(Long commentId, Long user, Long book, String content) {
        this.commentId = commentId;
        this.user = user;
        this.book = book;
        this.content = content;
    }
}
