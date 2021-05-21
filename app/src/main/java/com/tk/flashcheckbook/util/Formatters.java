package com.tk.flashcheckbook.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatters {


    //Currency and locale
    public void setLocaleAndCurrency() {
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

    }



    //Date
    public static String dateToString(Date date) {


        String pattern = "MM/dd/YY";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(date);

        return formattedDate;

    }


    public static Date stringToDate(String date) throws ParseException {


        DateFormat outputFormat = new SimpleDateFormat("MM/yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);


        String inputText = date;
        Date date2 = inputFormat.parse(inputText);
        //String outputText = outputFormat.format(date);


//        String pattern = "MM/dd/YY";
//        Date formattedDate = new SimpleDateFormat(pattern).parse(date);


        return date2;
    }

    public static String dateStringToFormattedDateString(String string) {



        String pattern = "MM/dd/YY";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(string);

        return formattedDate;



    }


    public static String cardPattern = "MMM d yyyy";

    private static String fullPattern = "MM/dd/yyyy";

    public static SimpleDateFormat fullDateFormat = new SimpleDateFormat(fullPattern);


}
