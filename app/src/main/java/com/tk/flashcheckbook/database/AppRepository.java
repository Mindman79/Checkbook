package com.tk.flashcheckbook.database;

import com.tk.flashcheckbook.util.SampleData;

import java.util.List;

public class AppRepository {


    private static final AppRepository ourInstance = new AppRepository();

    public List<Transaction> transactions;


    public static AppRepository getInstance() {

        return  ourInstance;
    }

    private AppRepository() {

        transactions = SampleData.getTestTransactions();

    }
}


