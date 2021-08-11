package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccountEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Account> liveAccount = new MediatorLiveData<>();





    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    static int globalcategoryId;
    static int globalpayeeId;



    public AccountEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
    }


    public void loadData(int accountId) {


        executor.execute(new Runnable() {
            @Override
            public void run() {


                Account account = repository.getAccountById(accountId);

                liveAccount.postValue(account);


            }
        });


    }


    public void getNextIDs() {


        globalpayeeId = repository.getNextAutoIncrementPayeeID();


        globalcategoryId = repository.getNextAutoIncrementCategoryID();

    }



    public void saveAccount(String name, String startBal, Date startDate) throws ParseException {


        Account account = liveAccount.getValue();



        if (account == null) {

            if (TextUtils.isEmpty(name.trim())) {

                return;

            }

            account = new Account();

            //getNextIDs();

            BigDecimal amountToDB = new BigDecimal(startBal);

            account.setName(name);
            account.setStartBal(amountToDB);
            account.setStartDate(startDate);

        } else {

            BigDecimal amountToDB = new BigDecimal(startBal);

            account.setName(name);
            account.setStartBal(amountToDB);
            account.setStartDate(startDate);


        }

        repository.insertAccount(account);

    }






    public void deleteAccount() {

        repository.deleteAccount(liveAccount.getValue());

    }


    public int getAccountCount() {

        return repository.getAccountCount();

    }
}