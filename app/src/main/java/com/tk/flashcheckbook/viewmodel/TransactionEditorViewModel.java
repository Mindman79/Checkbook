package com.tk.flashcheckbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Transaction;

public class TransactionEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Transaction> tLiveTransaction = new MediatorLiveData<>();
    private AppRepository repository;

    public TransactionEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }
}
