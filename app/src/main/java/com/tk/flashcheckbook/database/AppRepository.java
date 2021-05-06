package com.tk.flashcheckbook.database;

import android.content.Context;

import com.tk.flashcheckbook.util.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {


    private static AppRepository ourInstance;


    public List<Transaction> transactionList;
    private AppDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {

        if (ourInstance == null) {

            ourInstance = new AppRepository(context);
        }

        return  ourInstance;
    }

    private AppRepository(Context context) {

        transactionList = SampleData.getTestTransactions();
        db = AppDatabase.getInstance(context);

    }


    public void addSampleData() {


        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.transactionDao().insertAllTransactions(SampleData.getTestTransactions());
            }
        });

    }
}


