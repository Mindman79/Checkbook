package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tk.flashcheckbook.database.AppDatabase;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.util.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Transaction>> transactionsList;
    public LiveData<List<Payee>> payeesList;
    public LiveData<List<Category>> categoryList;
    private AppRepository repository;


    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        transactionsList = repository.transactionList;
        payeesList = repository.payeeList;
        categoryList = repository.categoryList;

    }


    public void addSampleData() {
        repository.addSampleData();

    }


    public void deleteAllNotes() {

        repository.deleteAllNotes();

    }
}
