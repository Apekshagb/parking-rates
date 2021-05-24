package com.sample.problem.parkingrates.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.service.ParkingRatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ParkingRatesController {

    @Autowired
    ParkingRatesService parkingRatesService;

    @GetMapping(path="/rates",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Rates> getRates(){
        //List<Rates> rates = parkingRatesService.getRates();

        return parkingRatesService.getRates();
    }

    @PutMapping(path="/rates",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Rates> updateRates(@RequestBody ParkingRates newRates){
        //List<Rates> rates = parkingRatesService.getRates();

        return parkingRatesService.updateRates(newRates);
    }

    @GetMapping(path="/price",produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectNode getPrice(@RequestParam String start, @RequestParam String end){

        String price =null;
        price =  parkingRatesService.getPrice(start,end);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        if (price == "0") {
            jsonObject.put("price","unavailable");
        }else{
            jsonObject.put("price",Integer.valueOf(price));
        }


        return jsonObject;
    }
}
