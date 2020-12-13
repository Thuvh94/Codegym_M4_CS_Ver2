package com.codegym.case4.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
public class RequestForm {
    private Long requestId;
    private User userId;
    private String title;
    private MultipartFile coverImg;
    private String description;
    private long publishedDate;
    private int pages;
    private String categories;
    private String author;
    private int requestStatus;
    private LocalDate createdAt;
    //    0: new, 1: đang xử lý, 2: đã xử lý xong
    public RequestForm() {
    }

    public RequestForm(Long requestId, User userId, String title, MultipartFile coverImg, String description, long publishedDate, int pages, String categories, String author, int requestStatus, Timestamp createdAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.title = title;
        this.coverImg = coverImg;
        this.description = description;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.categories = categories;
        this.author = author;
        this.requestStatus = requestStatus;
        this.createdAt = LocalDate.now();
    }
}
