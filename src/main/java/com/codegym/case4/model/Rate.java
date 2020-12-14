package com.codegym.case4.model;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "Rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book bookId;

    @ManyToOne
    private User userId;

    @Min(1) @Max(5)
    private int rate;

    public Rate() {
    }

    public Rate(Long id, Book bookId, User userId, @Min(1) @Max(5) int rate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.rate = rate;
    }

    public Rate(Book bookId, User userId, @Min(1) @Max(5) int rate) {
        this.bookId = bookId;
        this.userId = userId;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", rate=" + rate +
                '}';
    }
}
