package com.sample.problem.parkingrates.service;

import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;

import java.util.List;

public interface ParkingRatesService {

    public Iterable<Rates> getRates();

    public Rates save(Rates rate);

    public void saveAll(List<Rates> rates);

    public Iterable<Rates> updateRates(ParkingRates newRates);

    public String getPrice(String startDateTime, String endDateTime);
}
