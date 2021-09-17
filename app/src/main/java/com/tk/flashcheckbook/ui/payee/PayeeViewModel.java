package com.tk.flashcheckbook.ui.payee;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Payee;

import java.util.List;

public class PayeeViewModel extends AndroidViewModel {


    public LiveData<List<Payee>> payeeList;
    private AppRepository repository;


    public PayeeViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        payeeList = repository.payeeList;


    }


    public void deletePayee() {



    }
}
