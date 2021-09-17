package com.tk.flashcheckbook.ui.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Payee;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {


    public LiveData<List<Account>> accountList;
    private AppRepository repository;


    public AccountViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        accountList = repository.accountList;


    }


    public void deleteAccount() {



    }

    public LiveData<List<Account>> getAllAccounts() {


        MutableLiveData accounts = repository.getAllAccounts();

        return accounts;


    }
}
