package com.codegym.case4.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class BookForm {
    private Long bookId;
    private MultipartFile coverImg;
    private String title;
    private String description;
    private boolean isDeleted;
    private long publishedDate;
    private int pages;
    private Set<Category> categories;
    private Author authorId;

    public BookForm() {
    }

    public BookForm(Long bookId, MultipartFile coverImg, String title, String description, boolean isDeleted, long publishedDate, int pages, Set<Category> categories, Author authorId) {
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

    public BookForm(Long bookId, String title, String description, boolean isDeleted, long publishedDate, int pages, Set<Category> categories, Author authorId) {
        this.bookId = bookId;
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

    public MultipartFile getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(MultipartFile coverImg) {
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

    public long getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(long publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
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
