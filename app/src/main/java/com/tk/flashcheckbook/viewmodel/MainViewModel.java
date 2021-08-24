package com.tk.flashcheckbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<List<Transaction>> transactionsList;
    public MutableLiveData<List<Payee>> payeesList;
    public MutableLiveData<List<Category>> categoryList;
    private AppRepository repository;


    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        payeesList = repository.payeeList;
        categoryList = repository.categoryList;

    }


    public MutableLiveData<List<Transaction>> getAllTransactionsByAccountId(int accountId) {

        MutableLiveData transactions = repository.getAllTransactionsByAccountID(accountId);

        return transactions;

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


    public BigDecimal getTotalofAllTransactionsByAccountId(int accountId){

        return repository.getTotalofAllTransactionsByAccountId(accountId);

    }



    public int getAccountCount() {

        return repository.getAccountCount();

    }

    public String[] getAccountNames() {

        return repository.getAccountNames();

    }

    public Account getAccountByName(String name) {

        return repository.getAccountByName(name);

    }


    public BigDecimal getTotalofClearedTransactionsByAccountId(int accountId) {


        return repository.getTotalofAllClearedTransactionsByAccountId(accountId);



    }
}
