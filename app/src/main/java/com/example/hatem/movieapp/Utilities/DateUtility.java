package com.example.hatem.movieapp.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hatem on 9/23/16.
 */
public class DateUtility {


    public static String getFormattedMonthDay( String strDate ) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date = formatter.parse(strDate);
            SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMMM dd");
            String monthDayString = monthDayFormat.format(date.getTime());
            return monthDayString;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getFormattedyear( String strDate ) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date = formatter.parse(strDate);
            SimpleDateFormat monthDayFormat = new SimpleDateFormat("yyyy");
            String YearString = monthDayFormat.format(date.getTime());
            return YearString;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  null ;

    }

}
