package com.sample.problem.parkingrates;

import com.sample.problem.parkingrates.model.ParkingRates;
import com.sample.problem.parkingrates.model.Rates;
import com.sample.problem.parkingrates.repository.RatesRepository;
import com.sample.problem.parkingrates.service.ParkingRatesService;
import com.sample.problem.parkingrates.service.ParkingRatesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = ParkingRatesService.class)
public class ParkingRatesServiceTest {

    @InjectMocks
    private ParkingRatesServiceImpl parkingRatesService;

    @Mock
    private RatesRepository ratesRepository;

    @Test
    public  void  getAllRates(){

        List<Rates> listOfRates = new ArrayList<>();
        Rates rate1 = new Rates((long) 1,"mon,tues,thurs","0900-2100","America/Chicago",1500);
        Rates rate2 = new Rates((long) 2,"fri,sat,sun","0900-2100","America/Chicago",2000);
        Rates rate3= new Rates((long) 3,"wed","0600-1800","America/Chicago",1750);
        Rates rate4 = new Rates((long) 4,"mon,sat","0100-0500","America/Chicago",1000);
        Rates rate5 = new Rates((long) 5,"sun,tues","0100-0700","America/Chicago",925);
        listOfRates = Arrays.asList(rate1,rate2,rate3,rate4,rate5);

        Mockito.when(ratesRepository.findAll()).thenReturn(listOfRates);

        List<Rates> rates = (List<Rates>) parkingRatesService.getRates();

        assertEquals(5,rates.size());
    }

    @Test
    public  void  updateRates(){

        List<Rates> listOfRates = new ArrayList<>();
        Rates rate1 = new Rates((long) 1,"mon,tues,thurs","0900-2100","America/Chicago",1500);
        Rates rate2 = new Rates((long) 2,"fri,sat,sun","0900-2100","America/Chicago",2000);
        Rates rate3= new Rates((long) 3,"wed","0600-1800","America/Chicago",1750);
        Rates rate4 = new Rates((long) 4,"mon,sat","0100-0500","America/Chicago",1000);
        Rates rate5 = new Rates((long) 5,"sun,tues","0100-0700","America/Chicago",925);
        listOfRates = Arrays.asList(rate1,rate2,rate3,rate4,rate5);

        ParkingRates parkingRates = new ParkingRates();
        parkingRates.setRatesList(listOfRates);

        Mockito.when(ratesRepository.saveAll(listOfRates)).thenReturn(listOfRates);

        List<Rates> rates = (List<Rates>) parkingRatesService.updateRates(parkingRates);

        assertEquals(5,listOfRates.size());
    }
}
