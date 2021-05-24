package com.sample.problem.parkingrates.utils;

import com.sample.problem.parkingrates.service.ParkingRatesServiceImpl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatterHelper {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DateTimeFormatterHelper.class);

    public String getMilitaryTime(String time){

        String formattedTimestamp = null;

        try {
            DateTimeFormatter inputFormatter = DateTimeFormat.forPattern("HH:mm:ss");
            DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("HHmm");
            DateTime dateTime = inputFormatter.parseDateTime(String.valueOf(time));
            formattedTimestamp = outputFormatter.print(dateTime.getMillis());

            System.out.println("Final search time :" + formattedTimestamp);
        }catch(IllegalArgumentException e){
            Logger.error("Error while parsing the time to military format time:{}",time);
            e.printStackTrace();
        }

        return formattedTimestamp;
    }

    public String isoDateTimeFormat(String dateTime){
        

        Date date = null;
        String formattedDateString = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            date = formatter.parse(dateTime);
            formattedDateString = formatter.format(date);
            System.out.println("Formatted date :"+formattedDateString);
        } catch (ParseException e) {
            Logger.error("Error while parsing the request date and time :{}",dateTime);
            e.printStackTrace();
        }
        
        return formattedDateString;
    }

    public String getDayOfWeek(String dateTime){

        DateTime dt = new DateTime(dateTime);
        DateTime.Property shortWeek = dt.dayOfWeek();

        String dayofWeek = shortWeek.getAsShortText();

        return dayofWeek;
    }

    public String getTimeZone(String dateTime){

        DateTime dt = new DateTime(dateTime);
        String timeZone = String.valueOf(dt.getZone());

        return timeZone;
    }

    public String getDate(String dateTime){
        Date date = null;
        String formattedDateString = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = formatter.parse(dateTime);
            formattedDateString = formatter.format(date);
            System.out.println("Formatted date only :"+formattedDateString);
        } catch (ParseException e) {
            Logger.error("Error while parsing the request to get the requested date :{}",dateTime);
            e.printStackTrace();
        }

        return formattedDateString;
    }

}
