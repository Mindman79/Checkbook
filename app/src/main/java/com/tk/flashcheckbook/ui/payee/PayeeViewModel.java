package com.tk.flashcheckbook.ui.payee;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.util.List;

public class PayeeViewModel extends AndroidViewModel {


    //public MutableLiveData<List<Payee>> payeesList;
    private AppRepository repository;


    public PayeeViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());


    }


    public void deletePayee() {



    }


    public MutableLiveData<List<Payee>> getAllPayees() {

        MutableLiveData payees = repository.getAllPayees();

        return payees;

    }


}
