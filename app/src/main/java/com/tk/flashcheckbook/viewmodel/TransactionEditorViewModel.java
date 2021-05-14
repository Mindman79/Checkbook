package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Transaction;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TransactionEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Transaction> tLiveTransaction = new MediatorLiveData<>();
    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TransactionEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int transId) {


        executor.execute(new Runnable() {
            @Override
            public void run() {
                Transaction transaction = repository.getTransById(transId);
                tLiveTransaction.postValue(transaction);

            }
        });

    }


    public void saveTransaction(String transactionText) {

        Transaction transaction = tLiveTransaction.getValue();

            if (transaction == null) {

                if (TextUtils.isEmpty(transactionText.trim())) {

                    return;

                }

                transaction = new Transaction();

                transaction.setNote(transactionText.trim());

                //TODO: Add other fields to be captured and saved

        } else {


                transaction.setNote(transactionText.trim());
                //transaction.setAmount()


            }

            repository.insertTransaction(transaction);

    }
}
