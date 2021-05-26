package com.sample.problem.parkingrates;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.problem.parkingrates.data.ParkingRates;
import com.sample.problem.parkingrates.data.Rates;
import com.sample.problem.parkingrates.service.ParkingRatesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.List;

@SpringBootApplication
public class ParkingRatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingRatesApplication.class, args);
	}


	/*
		Bean to initialize, create and store the parking rates data in the database
	 */
	@Bean
	CommandLineRunner runner(ParkingRatesService parkingRatesService){
		return args -> {

			ObjectMapper mapper = new ObjectMapper();
			TypeReference<ParkingRates> typeReference = new TypeReference<ParkingRates>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/rates.json");
			try{
				ParkingRates parkingRates = mapper.readValue(inputStream,typeReference);
				parkingRatesService.saveAll(parkingRates.getRatesList());
				System.out.println("All rates are saved");
			}catch(IOException e){
				System.out.println("Unable to save rates: " + e.getMessage());
			}
		};
	}

}
