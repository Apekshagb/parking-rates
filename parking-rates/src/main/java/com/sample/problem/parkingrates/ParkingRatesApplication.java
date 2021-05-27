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


/**
 * The ParkingRatesApplication exposes 3 API's that can be used
 * to store, update and get the prices for a request day and time.
 *
 * @author  Apeksha Barhnapur
 * @version 1.0
 * @since   2021-05-23
 */
@SpringBootApplication
public class ParkingRatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingRatesApplication.class, args);
	}


	/**
		Bean to initialize, create and store the parking rates data in the database
		@param parkingRatesService - holds the data for list of parking rates
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
