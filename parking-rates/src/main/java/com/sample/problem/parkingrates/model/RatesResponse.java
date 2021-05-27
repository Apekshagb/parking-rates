package com.sample.problem.parkingrates.model;

public class RatesResponse {

    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RatesResponse{" +
                "price=" + price +
                '}';
    }
}
