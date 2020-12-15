package com.codegym.case4.model;

import lombok.Data;

@Data
public class RateForm {
    private Long id;
    private int rate;
    private Long userId;
    private Long bookId;

    public RateForm() {
    }

    public RateForm(Long id, int rate, Long userId, Long bookId) {
        this.id = id;
        this.rate = rate;
        this.userId = userId;
        this.bookId = bookId;
    }

}
