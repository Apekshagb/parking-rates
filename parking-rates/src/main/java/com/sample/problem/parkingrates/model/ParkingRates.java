package com.sample.problem.parkingrates.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ParkingRates {

    @JsonProperty("rates")
    private List<Rates> ratesList;

    public ParkingRates() {
    }

    public List<Rates> getRatesList() {
        return ratesList;
    }

    public void setRatesList(List<Rates> ratesList) {
        this.ratesList = ratesList;
    }

    @Override
    public String toString() {
        return "Rates{" +
                "ratesList=" + ratesList +
                '}';
    }
}
