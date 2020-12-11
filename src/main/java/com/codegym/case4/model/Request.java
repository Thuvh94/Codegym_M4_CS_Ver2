package model;

import com.codegym.case4.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    public Request(Long requestId, User userId, @NotEmpty String title, String coverImg, String description, @NotEmpty String author, int requestStatus) {
        this.requestId = requestId;
        this.userId = userId;
        this.title = title;
        this.coverImg = coverImg;
        this.description = description;
        this.author = author;
        this.requestStatus = requestStatus;
    }

    public Request() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
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
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
