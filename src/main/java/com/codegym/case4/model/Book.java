package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@Table(name= "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String coverImg;
    @NotEmpty
    private String title;
    private String description;
    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean isDeleted;
    private long publishedDate;
    private int pages;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;
    @ManyToOne
    private Author authorId;

    public Book() {
    }
    public Book(String coverImg, @NotEmpty String title, String description, Boolean isDeleted, Long publishedDate, Integer pages) {
        this.coverImg = coverImg;
        this.title = title;
        this.description = description;
        this.isDeleted = isDeleted;
        this.publishedDate = publishedDate;
        this.pages = pages;
    }

    public Book(Long bookId, @NotEmpty String title, String description, boolean isDeleted, long publishedDate, int pages, Set<Category> categories, Author authorId) {
        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.isDeleted = isDeleted;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.categories = categories;
        this.authorId = authorId;
    }

    public Book(Long bookId, String coverImg, @NotEmpty String title, String description, boolean isDeleted, long publishedDate, int pages, Set<Category> categories, Author authorId) {
        this.bookId = bookId;
        this.coverImg = coverImg;
        this.title = title;
        this.description = description;
        this.isDeleted = isDeleted;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.categories = categories;
        this.authorId = authorId;
    }

    public Book(String coverImg, @NotEmpty String title, String description, boolean isDeleted, long publishedDate, int pages, Set<Category> categories, Author authorId) {
        this.coverImg = coverImg;
        this.title = title;
        this.description = description;
        this.isDeleted = isDeleted;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.categories = categories;
        this.authorId = authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Long publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }
}
