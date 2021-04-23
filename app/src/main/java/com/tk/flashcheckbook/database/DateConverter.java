package com.tk.flashcheckbook.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {


    @TypeConverter
    public static Date ToDate(Long timestamp) {

        return timestamp == null ? null : new Date(timestamp);

    }

    @TypeConverter
    public static Long toTimestamp(Date date) {

        return date == null ? null : date.getTime();

    }

}



