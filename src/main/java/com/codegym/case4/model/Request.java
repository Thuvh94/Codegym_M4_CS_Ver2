package com.codegym.case4.model;

import com.codegym.case4.model.Category;
import com.codegym.case4.model.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Requests")
@Data
public class Request implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    @ManyToOne
    private User userId;

    @NotEmpty
    private String title;
    private String coverImg;
    private String description;
    private long publishedDate;
    private int pages;
    private String categories;
    @NotEmpty
    private String author;

    @Column(columnDefinition = "int default 0")
    private int requestStatus;
//    0: new, 1: đang xử lý, 2: đã xử lý xong, 3: từ chối thêm bởi Admin
    private LocalDate createdAt;
    public Request() {
    }

    public Request(Long requestId, User userId, @NotEmpty String title, String coverImg, String description, long publishedDate, int pages, String categories, @NotEmpty String author, int requestStatus, LocalDate createdAt) {
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

    public Request(User userId, @NotEmpty String title, String coverImg, String description, long publishedDate, int pages, String categories, @NotEmpty String author, int requestStatus, LocalDate createdAt) {
        this.userId = userId;
        this.title = title;
        this.coverImg = coverImg;
        this.description = description;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.categories = categories;
        this.author = author;
        this.requestStatus = requestStatus;
        this.createdAt = createdAt;
    }
}
