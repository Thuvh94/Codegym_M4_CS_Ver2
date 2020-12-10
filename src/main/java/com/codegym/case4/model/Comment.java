package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private String content;

    public Comment() {
    }

    public Comment(Long commentId, User user, Book book, String content) {
        this.commentId = commentId;
        this.user = user;
        this.book = book;
        this.content = content;
    }
}
