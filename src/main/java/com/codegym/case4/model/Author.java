package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @NotEmpty
    private String authorName;

    private String authorDesc;

    public Author() {
    }
    public Author(Long authorId, String authorName, String authorDesc) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorDesc = authorDesc;
    }
}
