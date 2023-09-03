package com.joliest.portfolios.groceryapi.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String DEFAULT_DATE_FORMAT = "MM-dd-yyyy";

    /**
     * Converts a Date to a String in default format (see DEFAULT_DATE_FORMAT)
     * @param date
     * @return a String date formatted as DEFAULT_DATE_FORMAT
     */
    public static String convertDateToDefaultFormat(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(date);
    }

    /**
     * Converts a string timestamp to a new Date
     * @param strDate (e.g. "2023-04-21T00:00:00.000Z")
     * @return a new Date
     */
    public static Date convertStrTimestampToDate(String strDate) {
        return Date.from(Instant.parse(strDate));
    }
}
