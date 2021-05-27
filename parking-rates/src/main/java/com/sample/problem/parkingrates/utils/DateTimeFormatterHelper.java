package com.sample.problem.parkingrates.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * The Helper calss to format various date and time fields
 *
 * @author  Apeksha Barhnapur
 * @version 1.0
 * @since   2021-05-23
 */
public class DateTimeFormatterHelper {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DateTimeFormatterHelper.class);

    /**
     * Takes the requested date/time and returns the time in military format
     * @param time
     * @return
     */
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


    /**
     * Takes the requested date/time and returns the value in "yyyy-MM-dd'T'HH:mm:ss" format
     *
     * @param dateTime
     * @return
     */
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


    /**
     * Get day of the week using the Joda time
     *
     * @param dateTime
     * @return
     */
    public String getDayOfWeek(String dateTime){

        DateTime dt = new DateTime(dateTime);
        DateTime.Property shortWeek = dt.dayOfWeek();

        String dayofWeek = shortWeek.getAsShortText();

        return dayofWeek;
    }

    /**
     * Get day of the timezone using the Joda time
     *
     * @param dateTime
     * @return
     */
    public String getTimeZone(String dateTime){

        DateTime dt = new DateTime(dateTime);
        String timeZone = String.valueOf(dt.getZone());

        return timeZone;
    }

    /**
     *
     * @param dateTime
     * @return
     */
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
