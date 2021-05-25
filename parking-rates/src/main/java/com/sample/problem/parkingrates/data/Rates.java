package com.sample.problem.parkingrates.data;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Parking rate details for a given day of week and time range")
public class Rates {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @ApiModelProperty(notes = "Rate ID will be auto generated")
    private Long id;

    @ApiModelProperty(notes = "Provide the short description of required day of week")
    private String days;

    @ApiModelProperty(notes = "Provide a valid time range in military format example:0700-1500 for 7am to 3 pm")
    private String times;

    @ApiModelProperty(notes = "The timezones specified in the JSON adhere to the 2017c version of the tz database ")
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
