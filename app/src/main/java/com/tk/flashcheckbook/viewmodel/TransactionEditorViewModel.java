package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Update;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TransactionEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Transaction> liveTransaction = new MediatorLiveData<>();
    public MutableLiveData<Payee> livePayee = new MediatorLiveData<>();
    public MutableLiveData<Category> liveCategory = new MediatorLiveData<>();

    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TransactionEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int transId, int payeeId, int categoryId) {


        executor.execute(new Runnable() {
            @Override
            public void run() {
                Transaction transaction = repository.getTransById(transId);

                Payee payee = repository.getPayeeById(payeeId);
                Category category = repository.getCategoryById(categoryId);

                liveTransaction.postValue(transaction);
                livePayee.postValue(payee);
                liveCategory.postValue(category);

            }
        });

    }


    //Convert strings to appropriate Entity types in this method
    public void saveTransaction(String amount, String note, Date date, String number, String payee, String category, Integer cleared) throws ParseException {


        //TODO: This is not saving a CategoryID or PayeeID, top priority
        Transaction transaction = liveTransaction.getValue();


        if (transaction == null) {

            if (TextUtils.isEmpty(amount.trim())) {

                return;

        }

        transaction = new Transaction();


            int payeeId = savePayee(payee, 6);
            int categoryId = saveCategory(category);

            Integer numberToDB = Integer.valueOf(number);
            BigDecimal amountToDB = new BigDecimal(amount);

            transaction.setAmount(amountToDB);
            transaction.setDate(date);
            //transaction.setClearedDate(transaction.getClearedDate());
            transaction.setPayeeId(payeeId);
            transaction.setCategoryId(categoryId);
            transaction.setNumber(numberToDB);
            transaction.setNote(note.trim());
            transaction.setCleared(cleared);



//        transaction.setAmount(transaction.getAmount());
//        transaction.setDate(transaction.getDate());
//        transaction.setClearedDate(transaction.getClearedDate());
//        transaction.setPayeeId(transaction.getPayeeId());
//        transaction.setCategoryId(transaction.getCategoryId());
//        transaction.setNumber(transaction.getNumber());
//        transaction.setNote(transaction.getNote().trim());
//        transaction.setCleared(transaction.getCleared());

        //TODO: Add other fields to be captured and saved
        //TODO: Fix bug that prevents long Strings from being saved as Ints
        //TODO: Fix bug that prevents transaction window from changed from "Add" to "Edit"

    } else {


        Integer numberToDB = Integer.valueOf(number);
        BigDecimal amountToDB = new BigDecimal(amount);

        transaction.setAmount(amountToDB);
        transaction.setDate(date);
        //transaction.setClearedDate(transaction.getClearedDate());
        transaction.setPayeeId(transaction.getPayeeId());
        transaction.setCategoryId(transaction.getCategoryId());
        transaction.setNumber(numberToDB);
        transaction.setNote(note.trim());
        transaction.setCleared(cleared);

    }

        repository.insertTransaction(transaction);

}

    public int savePayee(String name, int categoryId) {


        Payee payee = livePayee.getValue();
        int payeeId = payee.getId();


        if (payee == null) {

            payee = new Payee();

            payee.setName(name);
            payee.setCategoryId(categoryId);

        } else {

            payee.setName(name);
            payee.setCategoryId(categoryId);


        }


        repository.insertPayee(payee);



        return payeeId;
    }


    public int saveCategory(String name) {


        Category category = liveCategory.getValue();
        int categoryId = category.getId();

        if (category == null) {

            category = new Category();

            category.setName(name);


        } else {

            category.setName(name);


        }


        repository.insertCategory(category);


        return categoryId;

    }



}