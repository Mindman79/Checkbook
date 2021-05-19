package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.Date;
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


    //Convert strings to appropriate Entity types in this method
    public void saveTransaction(String amount, String note) {

        Transaction transaction = tLiveTransaction.getValue();


            if (transaction == null) {

                if (TextUtils.isEmpty(transaction.getNote().trim())) {

                    return;

                }

                transaction = new Transaction();


                transaction.setAmount(transaction.getAmount());
                transaction.setDate(transaction.getDate());
                transaction.setClearedDate(transaction.getClearedDate());
                transaction.setPayeeId(transaction.getPayeeId());
                transaction.setCategoryId(transaction.getCategoryId());
                transaction.setNumber(transaction.getNumber());
                transaction.setNote(transaction.getNote().trim());
                transaction.setCleared(transaction.getCleared());

                //TODO: Add other fields to be captured and saved

        } else {

                //TODO: convert amount to a BigDecimal at this stage

                BigDecimal amount2 = new BigDecimal(amount);
                transaction.setAmount(amount2);
                //transaction.setDate(transaction.getDate());
                //transaction.setClearedDate(transaction.getClearedDate());
                //transaction.setPayeeId(transaction.getPayeeId());
                //transaction.setCategoryId(transaction.getCategoryId());
                //transaction.setNumber(transaction.getNumber());
                transaction.setNote(note.trim());
                //transaction.setCleared(transaction.getCleared());

            }

            repository.insertTransaction(transaction);

    }
}
