package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tk.flashcheckbook.database.AppDatabase;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.util.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    public List<Transaction>  transactionsList;
    private AppRepository repository;


    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance();
        transactionsList = repository.transactionList;

    }


    public void addSampleData() {
        repository.addSampleData();

    }



}
