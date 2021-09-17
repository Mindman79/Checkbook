package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Payee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PayeeEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Payee> livePayee = new MediatorLiveData<>();


    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();


    public PayeeEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
    }


    public void loadData(int payeeId) {


        executor.execute(new Runnable() {
            @Override
            public void run() {


                Payee payee = repository.getPayeeById(payeeId);

                livePayee.postValue(payee);


            }
        });


    }




    public void savePayee(String name) throws ParseException {


        Payee payee = livePayee.getValue();

        if (payee == null) {

            if (TextUtils.isEmpty(name.trim())) {

                return;

            }

            payee = new Payee();

            payee.setName(name);


        } else {

            payee.setName(name);

        }

        repository.insertPayee(payee);

    }






    public void deletePayee() {

        repository.deletePayee(livePayee.getValue());

    }





}