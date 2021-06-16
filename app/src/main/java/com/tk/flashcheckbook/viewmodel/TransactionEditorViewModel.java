package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.lang.Integer.valueOf;

public class TransactionEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Transaction> liveTransaction = new MediatorLiveData<>();
    public MutableLiveData<Payee> livePayee = new MediatorLiveData<>();
    public MutableLiveData<Category> liveCategory = new MediatorLiveData<>();

    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    static int globalcategoryId;
    static int globalpayeeId;



    public TransactionEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
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


    public void getNextIDs() {


        globalpayeeId = repository.getNextAutoIncrementPayeeID();


        globalcategoryId = repository.getNextAutoIncrementCategoryID();

    }

    //Convert strings to appropriate Entity types in this method


    public void saveTransaction(String amount, String note, Date date, String number, Integer cleared) throws ParseException {




        Transaction transaction = liveTransaction.getValue();





        if (transaction == null) {

            if (TextUtils.isEmpty(amount.trim())) {

                return;

        }

        transaction = new Transaction();

            getNextIDs();

            System.out.println("Category ID:" + globalcategoryId);
            System.out.println("Payee ID: " + globalpayeeId);

            //Handle PayeeID and CategoryID increases, if not 0
//            if (payeeId == 0) {
//                payeeId = 0;
//            } else {
//                payeeId = payeeId + 1;
//            }
//
//            if (categoryId == 0) {
//                categoryId = 0;
//
//            } else {
//                categoryId = categoryId + 1;
//            }

            Integer numberToDB = valueOf(number);
            BigDecimal amountToDB = new BigDecimal(amount);

            transaction.setAmount(amountToDB);
            transaction.setDate(date);
            //transaction.setClearedDate(transaction.getClearedDate());
            transaction.setPayeeId(globalpayeeId + 1);
            transaction.setCategoryId(globalcategoryId + 1);
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


        Integer numberToDB = valueOf(number);
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

    public void savePayee(String name) {


        Payee payee = livePayee.getValue();




//        repository.getNextAutoIncrementPayeeID(new OnValueListener() {
//            @Override
//            public void onValue(int value) {
//                // use "value" which is returned from Room
//
//                payeeId = value + 1;
//
//            }
//        });
//
//        repository.getNextAutoIncrementCategoryID(new OnValueListener() {
//            @Override
//            public void onValue(int value) {
//                // use "value" which is returned from Room
//
//                categoryId = value;
//
//            }
//        });

        System.out.println("savePayee globalPayeeId" + globalpayeeId);

        if (payee == null) {

            payee = new Payee();
//
//            if (payeeId == 0) {
//                payeeId = 0;
//            } else {
//                payeeId = payeeId + 1;
//            }

//            if (categoryId == 0) {
//                categoryId = 0;
//            } else {
//                categoryId = categoryId + 1;
//            }


            payee.setName(name);
            payee.setId(globalpayeeId + 1);
            payee.setCategoryId(globalcategoryId + 1);

        } else {

            payee.setName(payee.getName());
            payee.setId(payee.getId());
            payee.setCategoryId(payee.getCategoryId());


        }

        repository.insertPayee(payee);

    }


    public void saveCategory(String name) {



//        repository.getNextAutoIncrementCategoryID(new OnValueListener() {
//            @Override
//            public void onValue(int value) {
//                // use "value" which is returned from Room
//
//                categoryId = value + 1;
//
//            }
//        });

        Category category = liveCategory.getValue();





        System.out.println("saveCategory globalCategoryId" + globalcategoryId);


        if (category == null) {

            category = new Category();
//
//            if (categoryId == 0) {
//
//                categoryId = 0;
//            } else {
//
//                categoryId = categoryId + 1;
//            }

            category.setName(name);
            category.setId(globalcategoryId + 1);


        } else {

            category.setName(category.getName());
            category.setId(category.getId());


        }


        repository.insertCategory(category);


    }


    public void deleteTransaction() {

        repository.deleteTransaction(liveTransaction.getValue());

    }
}