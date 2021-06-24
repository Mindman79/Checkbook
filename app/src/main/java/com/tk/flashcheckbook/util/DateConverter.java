package com.tk.flashcheckbook.util;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {


    @TypeConverter
    public static Date toDateFromTimestamp(Long timestamp) {

        return timestamp == null ? null : new Date(timestamp);

    }

    @TypeConverter
    public static Long toTimestampFromDate(Date date) {

        return date == null ? null : date.getTime();

    }

}



