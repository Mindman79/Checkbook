package com.tk.flashcheckbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.util.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<Transaction>  transactions = SampleData.getTestTransactions();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
