package com.sample.problem.parkingrates.service;

import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.repository.RatesRepository;
import com.sample.problem.parkingrates.utils.DateTimeFormatterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingRatesServiceImpl implements ParkingRatesService {

    @Autowired
    RatesRepository ratesRepository;

    DateTimeFormatterHelper dateTimeFormatterHelper;

    @Override
    public Iterable<Rates> getRates(){
        return ratesRepository.findAll();
    }

    @Override
    public Rates save(Rates rate){
        return ratesRepository.save(rate);
    }

    @Override
    public void saveAll(List<Rates> rates){
        ratesRepository.saveAll(rates);
    }

    @Override
    public Iterable<Rates> updateRates(ParkingRates newRates) {
         ratesRepository.deleteAll();
         ratesRepository.saveAll(newRates.getRatesList());

         return ratesRepository.findAll();
    }

    @Override
    public String getPrice(String startDateTime, String endDateTime) {

        dateTimeFormatterHelper = new DateTimeFormatterHelper();
        String startTime = dateTimeFormatterHelper.isoDateTimeFormat(startDateTime);
        String endTime = dateTimeFormatterHelper.isoDateTimeFormat(endDateTime);

        String parkingPrice = findParkingPrice(startTime,endTime);

        return parkingPrice;
    }

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

        if(startDate.equals(endDate)){
            List<Rates> rates = ratesRepository.findByDaysAndTimes(startDayOfWeek.toLowerCase());
            System.out.println("Rates :"+rates);

            if(rates.size() > 1){
                return "unavailable";
            }else{
                System.out.println("Inside else");
                Rates requiredRate = rates.get(0);
                String timeRange = requiredRate.getTimes();
                String[] timings = timeRange.split("-");
                System.out.println("Start time :"+timings[0]+" End Time :"+timings[1]);

                System.out.println("Request start time :"+startParkingTime+" end time : "+endParkingTime);

                if(Integer.valueOf(startParkingTime)> Integer.valueOf(timings[0]) &&
                        Integer.valueOf(endParkingTime) < Integer.valueOf(timings[1])){
                    System.out.println("True");
                }

            }
        }else{
            return "unavailable";
        }

        return "price";
    }
}
