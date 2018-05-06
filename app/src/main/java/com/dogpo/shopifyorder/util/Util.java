package com.dogpo.shopifyorder.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static String convertServerDateToReadableDate(String s) {
        //Wed, May 31 2017 07:08 PM
        //MM-dd-yyyy hh:mm a
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = null;
        try {
            date = fmt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            return fmtOut.format(date);
        } catch (Exception ex) {
            return "No date";
        }

    }

    public static int getYear(String s) {
        //Wed, May 31 2017 07:08 PM
        //MM-dd-yyyy hh:mm a
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = null;
        try {
            date = fmt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

}
