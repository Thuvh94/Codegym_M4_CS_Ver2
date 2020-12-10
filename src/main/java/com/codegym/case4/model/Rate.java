package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Data
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

}
