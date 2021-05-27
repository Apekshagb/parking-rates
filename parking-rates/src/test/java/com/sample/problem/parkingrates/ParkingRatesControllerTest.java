package com.sample.problem.parkingrates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.problem.parkingrates.controller.ParkingRatesController;
import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.data.RatesResponse;
import com.sample.problem.parkingrates.service.ParkingRatesService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ParkingRates parkingRates;
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

        parkingRates = new ParkingRates();
        parkingRates.setRatesList(listOfRates);

        price = "2000";
    }

    @Test
    public void getAllRates() throws Exception{

        Mockito.when(parkingRatesService.getRates()).thenReturn(listOfRates);

        mockMvc.perform(get("/api/rates")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].days",is(listOfRates.get(0).getDays())))
                .andExpect(jsonPath("$[0].times",is(listOfRates.get(0).getTimes())))
                .andExpect(jsonPath("$[0].tz",is(listOfRates.get(0).getTz())))
                .andExpect(jsonPath("$[0].price", is(listOfRates.get(0).getPrice())));
    }

    @Test
    public void putAllRates() throws Exception{

        Rates rate = new Rates((long) 1,"mon,tues,thurs","0900-2100","America/Chicago",1500);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/rates")
                .content(asJsonString(rate))
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());
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
                .andExpect(jsonPath("$.price").value("2000"));
    }

    @Test
    public void getPriceNotAvailable() throws Exception{

        Mockito.when(parkingRatesService.getPrice("2015-07-04T07:00:00+05:00","2015-07-04T20:00:00+05:00")).thenReturn("unavailable");

        MultiValueMap<String,String> mockQueryParam = new LinkedMultiValueMap<>();
        mockQueryParam.add("start","2015-07-04T07:00:00+05:00");
        mockQueryParam.add("end","2015-07-04T20:00:00+05:00");

        mockMvc.perform(get("/api/price").queryParams(mockQueryParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.price").value("unavailable"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
