package model;

import com.codegym.case4.model.Book;
import com.codegym.case4.model.User;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
