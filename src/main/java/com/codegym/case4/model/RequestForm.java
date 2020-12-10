package com.codegym.case4.model;

import org.springframework.web.multipart.MultipartFile;

public class RequestForm {
    private Long requestId;
    private User user;
    private String title;
    private MultipartFile coverImg;
    private String description;
    private String author;
    private int requestStatus;

    public RequestForm() {
    }

    public RequestForm(Long requestId, User user, String title, MultipartFile coverImg, String description, String author, int requestStatus) {
        this.requestId = requestId;
        this.user = user;
        this.title = title;
        this.coverImg = coverImg;
        this.description = description;
        this.author = author;
        this.requestStatus = requestStatus;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(MultipartFile coverImg) {
        this.coverImg = coverImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
