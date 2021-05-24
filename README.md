# parking-rates  
**Project Name:** 	:smile:**Parking Rates API**	:smile:  
**Scope:** An API that allows a user to enter a date time range and get back the price at which they would be charged to park for that time span:star:  

**Description:**  
:sparkle: Rates(/rates)  
               :large_blue_diamond: This path takes a PUT where rate information can be updated by submitting a modified rates JSON. Thi submitted JSON overwrites the stored rates.  
               :large_blue_diamond: A rate is comprised of a price, time range the rate is valid, and days of the week the rate applies to  
               :large_blue_diamond: This path when requested with a GET returns the rates stored.  
:sparkle: Price(/price)  
               :large_blue_diamond: The second endpoint is price. It allows the user to request the price for a requested time.  
               :large_blue_diamond: It uses query parameters for requesting the price. The user specifies input date/times as ISO-8601 with timezones  
               :large_blue_diamond: The paramters are start and end. An example query is ?start=2015-07-01T07:00:00-05:00&end=2015-07-01T12:00:00-05:00  
               :large_blue_diamond: Response contains price  
                          {  
                          "price": 5000  
                          }  

**Built With**  
Java  
SpringBoot  
JPA  
Swagger  

**Getting Started**  
This is an example of how you may give instructions on setting up your project locally. To get a local copy up and running follow these simple example steps.  

**Prerequisites**  
Kindly make sure the following softwares/tools have been installed on your system before you clone and run the application.  

:white_check_mark: IDE to run the spring boot application  
:white_check_mark: Java 11  
:white_check_mark: Maven 3.x version  

**Installation**  
:white_check_mark: Clone the repo  
:white_check_mark: You can run the following command via your command line " git clone https://github.com/Apekshagb/parking-rates.git "  
:white_check_mark: Import project into IDE as a maven project   
:white_check_mark: Build the application  

**Running**  
:white_check_mark: Run the SprintBootApplication (ParkingRatesApplication.java) -> You can right click within the file and run the app or run at the app level  
:white_check_mark: Open a browser or portman and you can access the endpoint on you localhost via http://localhost:5000/api/* . 
 Below are the list of API's that you can access:  
    :eight_pointed_black_star: Get the list of existing rates (GET method)- http://localhost:5000/api/rates  
    :eight_pointed_black_star: Update the existing rates with a newwer set of rates (PUT Method) - http://localhost:5000/api/rates  
    :eight_pointed_black_star: Get parking price for a given date and time range (GET method) - http://localhost:5000/api/price?start=&end=  
    
 
**Swagger document will be available soon**  

**Contact**  
Apeksha Barhanpur; apeksha.gb@gmail.com  
