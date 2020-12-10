package com.codegym.case4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "Requests")
public class Request implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    @ManyToOne
    private User userId;

    @NotEmpty
    private String title;
    private String coverImg;
    private String description;

    @NotEmpty
    private String author;

    @Column(columnDefinition = "int default 0")
    private int requestStatus;

    public Request() {
    }

    public Request(Long requestId, User userId, @NotEmpty String title, String coverImg, String description, @NotEmpty String author, int requestStatus) {
        this.requestId = requestId;
        this.userId = userId;
        this.title = title;
        this.coverImg = coverImg;
        this.description = description;
        this.author = author;
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
