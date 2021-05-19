package com.tk.flashcheckbook.database;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class CurrencyConverter {

    BigDecimal bd = new BigDecimal(1223.456);
    int test = bd.scaleByPowerOfTen(4).intValue();

    @TypeConverter
    public static BigDecimal toBigDecimal(int integer) {

        BigDecimal bd = new BigDecimal(integer);

        return bd.scaleByPowerOfTen(-2) ;

    }

    @TypeConverter
    public static Integer toInteger(BigDecimal bd) {

        Integer integer = bd.scaleByPowerOfTen(2).intValue();

        return integer;

    }


}
