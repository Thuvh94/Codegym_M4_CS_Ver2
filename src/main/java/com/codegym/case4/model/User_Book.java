package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="User_book")
public class User_Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book bookId;

    @ManyToOne
    private User userId;

    private int status;

    public User_Book() {
    }

    public User_Book(Long id, Book bookId, User userId, int status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User_Book{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
