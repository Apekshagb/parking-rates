package com.sample.problem.parkingrates.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatterHelper {

    public String getMilitaryTime(String time){

        org.joda.time.format.DateTimeFormatter inputFormatter = DateTimeFormat.forPattern("HH:mm:ss");
        org.joda.time.format.DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("HHmm");
        DateTime dateTime = inputFormatter.parseDateTime(String.valueOf(time));
        String formattedTimestamp = outputFormatter.print(dateTime.getMillis());

        System.out.println("Final search time :"+formattedTimestamp);

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
            e.printStackTrace();
        }

        return formattedDateString;
    }

}
