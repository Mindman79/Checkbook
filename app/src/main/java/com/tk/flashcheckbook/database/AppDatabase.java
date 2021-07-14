package com.tk.flashcheckbook.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tk.flashcheckbook.util.CurrencyConverter;
import com.tk.flashcheckbook.util.DateConverter;

@Database(entities = {Account.class, Category.class, Payee.class, Transaction.class}, exportSchema = false, version = 7)
@TypeConverters({DateConverter.class, CurrencyConverter.class})

public abstract class AppDatabase extends RoomDatabase {


    //TODO: Figure out how to migrate database versions

    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;

    //Object to lock synchronizing with
    private static final Object LOCK = new Object();


    public abstract CategoryDao categoryDao();
    public abstract AccountDao accountDao();
    public abstract PayeeDao payeeDao();
    public abstract TransactionDao transactionDao();



    public static AppDatabase getInstance(Context context) {

        if (instance == null) {

            synchronized (LOCK) {

                if (instance == null) {

                    instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
        }


        }

        return instance;
    }
}
