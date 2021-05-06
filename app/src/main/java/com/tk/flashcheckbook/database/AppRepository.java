package com.tk.flashcheckbook.database;

import android.content.Context;

import com.tk.flashcheckbook.util.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {


    private static final AppRepository ourInstance = new AppRepository();


    public List<Transaction> transactionList;
    private AppDatabase db;

    public static AppRepository getInstance() {


        return  ourInstance;
    }

    private AppRepository() {

        transactionList = SampleData.getTestTransactions();

    }


    public void addSampleData() {



    }
}


