package com.sample.problem.parkingrates;

import com.sample.problem.parkingrates.controller.ParkingRatesController;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.data.RatesResponse;
import com.sample.problem.parkingrates.service.ParkingRatesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = ParkingRatesController.class)
public class ParkingRatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ParkingRatesController parkingRatesController;

    @Mock
    private ParkingRatesService parkingRatesService;

    private List<Rates> listOfRates;

    private String price = "0";
    @Before
    public void prepareData() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingRatesController).build();
        Rates rate1 = new Rates((long) 1,"mon,tues,thurs","0900-2100","America/Chicago",1500);
        Rates rate2 = new Rates((long) 2,"fri,sat,sun","0900-2100","America/Chicago",2000);
        Rates rate3= new Rates((long) 3,"wed","0600-1800","America/Chicago",1750);
        Rates rate4 = new Rates((long) 4,"mon,sat","0100-0500","America/Chicago",1000);
        Rates rate5 = new Rates((long) 5,"sun,tues","0100-0700","America/Chicago",925);
        listOfRates = Arrays.asList(rate1,rate2,rate3,rate4,rate5);

        price = "2000";


    }

    @Test
    public void getRates() throws Exception{

        Mockito.when(parkingRatesService.getRates()).thenReturn(listOfRates);

        mockMvc.perform(get("/api/rates")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].days",is(listOfRates.get(0).getDays())))
                .andExpect(jsonPath("$[0].times",is(listOfRates.get(0).getTimes())))
                .andExpect(jsonPath("$[0].tz",is(listOfRates.get(0).getTz())))
                .andExpect(jsonPath("$[0].price", is(listOfRates.get(0).getPrice())));
    }

    @Test
    public void getPrice() throws Exception{

        Mockito.when(parkingRatesService.getPrice("2021-05-28T09:00:00-05:00","2021-05-28T12:00:00-05:00")).thenReturn(price);

        MultiValueMap<String,String> mockQueryParam = new LinkedMultiValueMap<>();
        mockQueryParam.add("start","2021-05-28T09:00:00-05:00");
        mockQueryParam.add("end","2021-05-28T12:00:00-05:00");

        mockMvc.perform(get("/api/price").queryParams(mockQueryParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$[0].price", "2000"));
    }
}
