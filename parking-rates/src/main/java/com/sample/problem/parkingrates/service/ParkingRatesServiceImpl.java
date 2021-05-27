package com.sample.problem.parkingrates.service;

import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.repository.RatesRepository;
import com.sample.problem.parkingrates.utils.DateTimeFormatterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * The ParkingRatesApplication service class that comprises of all the DAO calls
 *and function to get the price for a given dateTime
 *
 * @author  Apeksha Barhnapur
 * @version 1.0
 * @since   2021-05-23
 */
@Service
public class ParkingRatesServiceImpl implements ParkingRatesService {
    private static final Logger Logger = LoggerFactory.getLogger(ParkingRatesServiceImpl.class);

    @Autowired
    RatesRepository ratesRepository;

    DateTimeFormatterHelper dateTimeFormatterHelper;

    /**
        Gets the list of all the existing parking rates for a given day of the week and time range
     */
    @Override
    public Iterable<Rates> getRates(){
        return ratesRepository.findAll();
    }

    /**
        Saves a single rate object that comprises of the day of the week, timezone, timerange and price
     */
    @Override
    public Rates save(Rates rate){
        return ratesRepository.save(rate);
    }

    /**
        Saves the list of rates from the JSON object during application startip
        @param rates - list of rates to be save during application startup
     */
    @Override
    public void saveAll(List<Rates> rates){

        try{
            ratesRepository.saveAll(rates);
        }catch (IllegalArgumentException e){
            Logger.error(" Exception while saving the rates message :{} ",e.getMessage());
            e.printStackTrace();
        }

    }

    /**
        Replaces/overwrites all the existing parking rates with the newly provided rates via the API request
        @param newRates - list of new rates that will overwrite the existing
     */
    @Override
    public Iterable<Rates> updateRates(ParkingRates newRates) {
        ratesRepository.deleteAll();
        Iterable<Rates> rates;

        if(newRates != null){
            try {
                rates = ratesRepository.saveAll(newRates.getRatesList());

                if(rates != null){
                    Logger.info(" Parking rates saved successfully");
                }else{
                    Logger.warn(" Encountered issues while updating the parking rates");
                }
            }catch (IllegalArgumentException e){
                Logger.error(" Exception while updating the rates, requestedRates : {} , message :{} ",newRates, e.getMessage());
                e.printStackTrace();
            }
        }else{
            Logger.info(" Not valid parking rates provided via API request");
        }

         return ratesRepository.findAll();
    }

    /**
        Get parking price for a give start and end date+time range
        @param startDateTime
        @param  endDateTime
        @return parkingPrice
     */
    @Override
    public String getPrice(String startDateTime, String endDateTime) {

        String parkingPrice=null;

        if(startDateTime != null && endDateTime != null){
            dateTimeFormatterHelper = new DateTimeFormatterHelper();
            String startTime = dateTimeFormatterHelper.isoDateTimeFormat(startDateTime);
            String endTime = dateTimeFormatterHelper.isoDateTimeFormat(endDateTime);

            parkingPrice = findParkingPrice(startTime,endTime);
            System.out.println("Price inside getPrice :"+parkingPrice);

            if(parkingPrice != null){
                Logger.info("Parking price for the given request is available : {}",parkingPrice);
                return parkingPrice;
            }else if(parkingPrice == "unavailable"){
                Logger.info("Kindly provide valida start and end dates");
                parkingPrice="0";
            }
        }

        return parkingPrice;
    }


    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private String findParkingPrice(String startTime,String endTime){
        dateTimeFormatterHelper = new DateTimeFormatterHelper();

        String startDayOfWeek = dateTimeFormatterHelper.getDayOfWeek(startTime);
        String endDayOfWeek = dateTimeFormatterHelper.getDayOfWeek(endTime);

        String startDate = dateTimeFormatterHelper.getDate(startTime);
        String endDate = dateTimeFormatterHelper.getDate(startTime);

        String[] startTimes = startTime.split("T");
        String[] endTimes = endTime.split("T");
        String startParkingTime = dateTimeFormatterHelper.getMilitaryTime(startTimes[1]);
        String endParkingTime = dateTimeFormatterHelper.getMilitaryTime(endTimes[1]);

        String timeZone = dateTimeFormatterHelper.getTimeZone(startTime);
        List<String> parkingPrice;

        if(startDate.equals(endDate)){
            List<Rates> rates = ratesRepository.findByDaysAndTimes(startDayOfWeek.toLowerCase());
            Logger.info("List of rates for the requested date and time range Rates : {}",rates);

            parkingPrice = new ArrayList<>();

            for(Rates requiredRate : rates){
                String timeRange = requiredRate.getTimes();
                String[] timings = timeRange.split("-");

                if(Integer.valueOf(startParkingTime)>= Integer.valueOf(timings[0]) &&
                        Integer.valueOf(endParkingTime) <= Integer.valueOf(timings[1]) && timeZone.equals(requiredRate.getTz())){
                    parkingPrice.add(String.valueOf(requiredRate.getPrice()));
                }
            }

            if(parkingPrice.size() > 1 || parkingPrice.size() == 0){
                return "0";
            }

        }else{
            return "0";
        }

        Logger.info("Successfully found the parking for given request (date and time) StartTime : {} EndTime : {} ParkingPrice :{}",startTimes,endTime,parkingPrice);
        return parkingPrice.get(0);
    }
}
