package com.sample.problem.parkingrates.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sample.problem.parkingrates.model.ParkingRates;
import com.sample.problem.parkingrates.model.Rates;
import com.sample.problem.parkingrates.model.RatesResponse;
import com.sample.problem.parkingrates.service.ParkingRatesService;

import com.sample.problem.parkingrates.utils.SwaggerConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The ParkingRatesApplication Controller class exposes 3 API's that can be used
 * to store, update and get the prices for a request day and time.
 *
 * @author  Apeksha Barhnapur
 * @version 1.0
 * @since   2021-05-23
 */

@RestController
@RequestMapping("/api")
@ApiResponses(value = { @ApiResponse(code = 200, message = SwaggerConstants.SUCCESS),
        @ApiResponse(code = 400, message = SwaggerConstants.INVALID_INPUT_DATA),
        @ApiResponse(code = 404, message = SwaggerConstants.NOT_FOUND),
        @ApiResponse(code = 500, message = SwaggerConstants.INTERNAL_SERVER_ERROR) })
public class ParkingRatesController {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ParkingRatesController.class);

    @Autowired
    ParkingRatesService parkingRatesService;

    @ApiOperation(value = "Get list of all the available parking rates", response = Rates.class)
    @GetMapping(path="/rates",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Rates> getRates(){
        return parkingRatesService.getRates();
    }

    @PutMapping(path="/rates",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update/overwrite all the existing rates with new ones", response = Rates.class)
    public Iterable<Rates> updateRates(@RequestBody ParkingRates newRates){
        Iterable<Rates> updatedRates;

        if(newRates == null){
            return (Iterable<Rates>) new ResponseEntity<String>("Invalid request need to provide list of rates", HttpStatus.BAD_REQUEST);
        }

        updatedRates =  parkingRatesService.updateRates(newRates);

        if(updatedRates == null){
            return  (Iterable<Rates>)new ResponseEntity<>("Rates were not updated successfully", HttpStatus.OK);
        }

        return  updatedRates;
    }

    @GetMapping(path="/price",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get parking price for a given day and time range", response = RatesResponse.class)
    public RatesResponse getPrice(@RequestParam String start, @RequestParam String end){

        if(start == null || end == null){
            Logger.error("Invalid request parameters, need to provide both start and end date/time to get the parking price start status :{}",HttpStatus.BAD_REQUEST);
        }
        String price =null;
        RatesResponse ratesResponse = new RatesResponse();
        price =  parkingRatesService.getPrice(start,end);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        if (price == "0") {
            ratesResponse.setPrice("unavailable");
        }else{
            ratesResponse.setPrice(price);
        }

        Logger.info("Successfully fetch the parking price for given start and end date/time");
        return ratesResponse;
    }
}
