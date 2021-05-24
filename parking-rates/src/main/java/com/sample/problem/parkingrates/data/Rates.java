package com.sample.problem.parkingrates.data;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rates {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String days;
    private String times;
    private String tz;
    private int price;


    public Rates() {
    }

    public Rates(Long id, String days, String times, String tz, int price) {
        this.id = id;
        this.days = days;
        this.times = times;
        this.tz = tz;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rates{" +
                "id=" + id +
                ", days='" + days + '\'' +
                ", times='" + times + '\'' +
                ", tz='" + tz + '\'' +
                ", price=" + price +
                '}';
    }
}
