package com.sample.problem.parkingrates.controller;

import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.service.ParkingRatesService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@RestController
@RequestMapping("/api")
public class ParkingRatesController {

    @Autowired
    ParkingRatesService parkingRatesService;

    @GetMapping(path="/rates")
    public Iterable<Rates> getRates(){
        //List<Rates> rates = parkingRatesService.getRates();

        return parkingRatesService.getRates();
    }

    @PutMapping(path="/rates")
    public Iterable<Rates> updateRates(@RequestBody ParkingRates newRates){
        //List<Rates> rates = parkingRatesService.getRates();

        return parkingRatesService.updateRates(newRates);
    }

    @GetMapping("/price")
    public Integer getPrice(@RequestParam String start, @RequestParam String end){
        String price =null;

        System.out.println("Today date :"+ new Date().getTime());
        System.out.println("Start Time :"+start+ " End Time : "+end);

//        String[] timerange = formattedDateString.split("T");
//        System.out.println("After spilt = "+timerange[1]);

       price =  parkingRatesService.getPrice(start,end);

        return 0;
    }
}
