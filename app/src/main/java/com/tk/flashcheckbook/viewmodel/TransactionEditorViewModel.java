package com.tk.flashcheckbook.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.database.TransactionDao;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.lang.Integer.valueOf;

public class TransactionEditorViewModel extends AndroidViewModel {


    public MutableLiveData<Transaction> liveTransaction = new MutableLiveData<>();
    public MutableLiveData<Payee> livePayee = new MutableLiveData<>();
    public MutableLiveData<Category> liveCategory = new MutableLiveData<>();

    //public MutableLiveData<String> payeeNameQueryLiveData = new MediatorLiveData<>();


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


    public void saveTransaction(Integer accountId, String payee, String amount, String note, Date date, String number, Integer cleared) throws ParseException {


        Transaction transaction = liveTransaction.getValue();


        if (transaction == null) {

            if (TextUtils.isEmpty(amount.trim())) {

                return;

            }

            transaction = new Transaction();

            getNextIDs();

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

            //Integer numberToDB = Integer.parseInt(number);
            BigDecimal amountToDB = new BigDecimal(amount);

            transaction.setAccountId(accountId);
            transaction.setAmount(amountToDB);
            transaction.setDate(date);
            //transaction.setClearedDate(transaction.getClearedDate());
            transaction.setPayeeId(globalpayeeId + 1);
            transaction.setCategoryId(globalcategoryId + 1);
            transaction.setNumber(number.trim());
            transaction.setNote(note.trim());
            transaction.setCleared(cleared);


            //TODO: Add other fields to be captured and saved
            //TODO: Fix bug that prevents transaction window from changed from "Add" to "Edit"
            //TODO: Fix bug that creates more than one entry for automatically populated Categories

        } else {


            int initialPayeeID = transaction.getPayeeId();
            int checkedPayeeID = repository.getPayeeIDByName(payee);

            if (checkedPayeeID != initialPayeeID) {


                transaction.setPayeeId(checkedPayeeID);


            } else {

                transaction.setPayeeId(transaction.getPayeeId());

            }

            //Integer numberToDB = Integer.parseInt(number);
            BigDecimal amountToDB = new BigDecimal(amount);

            transaction.setAccountId(accountId);
            transaction.setAmount(amountToDB);
            transaction.setDate(date);
            //transaction.setClearedDate(transaction.getClearedDate());
            transaction.setPayeeId(transaction.getPayeeId());
            transaction.setCategoryId(transaction.getCategoryId());
            transaction.setNumber(number.trim());
            transaction.setNote(note.trim());
            transaction.setCleared(cleared);

        }

        repository.insertTransaction(transaction);

    }

    public void savePayee(String name) {


        Payee payee = livePayee.getValue();


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


            int initialPayeeID = payee.getId();
            int checkedPayeeID = repository.getPayeeIDByName(name);

                if (checkedPayeeID != initialPayeeID) {


                    payee.setId(checkedPayeeID);


                } else {

                    payee.setId(payee.getId());




                }

            payee.setName(name);
            //payee.setId(checkedPayeeID);
            payee.setCategoryId(payee.getCategoryId());


        }

        repository.insertPayee(payee);

    }


    public void saveCategory(String name) {




        Category category = liveCategory.getValue();


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

            //TODO: Fix category and payee saving bug here

        } else {

            category.setName(category.getName());
            category.setId(category.getId());


        }


        repository.insertCategory(category);


    }


    public void deleteTransaction() {

        repository.deleteTransaction(liveTransaction.getValue());

    }

    public Payee getPayeeById(int payeeId) {


        //AppRepository repository = new AppRepository(getPayeeById(pay));


        Payee payee = repository.getPayeeById(payeeId);
        //Payee payee = repository.getPayeeById(payeeId);


        return payee;

    }

    public String[] getAllPayeesByName(String name) {


        String[] list;

        list = repository.getAllPayeesByName(name);


        return list;
    }

    public String[] getAllCategoriesByName(String name) {


        String[] list;

        list = repository.getAllCategoriesByName(name);


        return list;
    }


    public Category getAssociatedCategoryIDByPayeeName(String name) {

        int categoryId = repository.getAssociatedCategoryIDByPayeeName(name);

        Category category = repository.getCategoryById(categoryId);

        return category;

    }

}