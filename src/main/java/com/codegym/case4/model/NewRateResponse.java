package com.codegym.case4.model;

import lombok.Data;

@Data
public class NewRateResponse {
    Rate rate;
    int totalRate;
    float average;

    public NewRateResponse() {
    }

    public NewRateResponse(Rate rate, int totalRate, float average) {
        this.rate = rate;
        this.totalRate = totalRate;
        this.average = average;
    }
}
