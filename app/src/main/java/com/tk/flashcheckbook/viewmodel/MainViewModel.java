package com.tk.flashcheckbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.List;

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

    public BigDecimal getTotalofClearedTransactions(){

        return repository.getTotalofClearedTransactions();

    }

    public BigDecimal getTotalofAllTransactions(){

        return repository.getTotalofAllTransactions();

    }


    public int getAccountCount() {

        return repository.getAccountCount();

    }
}
