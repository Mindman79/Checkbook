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




    public void saveTransaction(Integer accountId, String payee, String category, String amount, String note, Date date, String number, Integer cleared) throws ParseException {

        int payeeId = savePayee(payee);
        int categoryId = saveCategory(category);


        Transaction transaction = liveTransaction.getValue();


        if (transaction == null) {

            if (TextUtils.isEmpty(amount.trim())) {

                return;

            }

            transaction = new Transaction();

            BigDecimal amountToDB = new BigDecimal(amount);

            transaction.setAccountId(accountId);
            transaction.setAmount(amountToDB);
            transaction.setDate(date);
            //transaction.setClearedDate(transaction.getClearedDate());
            transaction.setPayeeId(payeeId);
            transaction.setCategoryId(categoryId);
            transaction.setNumber(number.trim());
            transaction.setNote(note.trim());
            transaction.setCleared(cleared);


            //TODO: Add other fields to be captured and saved
            //TODO: Fix bug that prevents transaction window from changed from "Add" to "Edit"
            //TODO: Fix bug that creates more than one entry for automatically populated Categories

        } else {


            //Integer numberToDB = Integer.parseInt(number);
            BigDecimal amountToDB = new BigDecimal(amount);

            transaction.setAccountId(accountId);
            transaction.setAmount(amountToDB);
            transaction.setDate(date);
            //transaction.setClearedDate(transaction.getClearedDate());
            transaction.setPayeeId(payeeId);
            transaction.setCategoryId(categoryId);
            transaction.setNumber(number.trim());
            transaction.setNote(note.trim());
            transaction.setCleared(cleared);

        }

        repository.insertTransaction(transaction);

    }

    public int savePayee(String name) {


        //TODO: Fix multiple instances of Payee screen being opened when clicking it more than once
        int globalcategoryId = repository.getNextAutoIncrementCategoryID();
        int globalpayeeId = repository.getNextAutoIncrementPayeeID();



        int payeeId = 0;

        Payee payee = livePayee.getValue();


        if (payee == null) {

            payee = new Payee();

            payee.setName(name);
            payee.setId(globalpayeeId + 1);
            payee.setCategoryId(globalcategoryId + 1);

            payeeId = globalpayeeId + 1;

        } else {

            int initialPayeeID = payee.getId();
            int checkedPayeeID = repository.getPayeeIDByName(name);

            //New payee
            if (checkedPayeeID == 0) {

                payee.setName(name);
                payee.setId(globalpayeeId + 1);
                payee.setCategoryId(payee.getCategoryId());

                payeeId = globalpayeeId + 1;

            //Exiting payee, but different from initial one
            } else if (initialPayeeID != checkedPayeeID) {

                payee.setName(name);
                payee.setId(checkedPayeeID);
                payee.setCategoryId(payee.getCategoryId());

                payeeId = checkedPayeeID;


            //Same payee
            } else {

                payee.setName(name);
                payee.setId(initialPayeeID);
                payee.setCategoryId(payee.getCategoryId());

                payeeId = initialPayeeID;

            }


        }


        repository.insertPayee(payee);

        return payeeId;


    }




    public int saveCategory(String name) {


        int globalcategoryId = repository.getNextAutoIncrementCategoryID();

        int categoryId = 0;

        Category category = liveCategory.getValue();


        if (category == null) {

            category = new Category();

            category.setName(name);
            category.setId(globalcategoryId + 1);
            categoryId = globalcategoryId + 1;

        } else {

            int initialCategoryID = category.getId();
            int checkedCategoryID = repository.getCategoryIDByName(name);

            //New category
            if (checkedCategoryID == 0) {

                category.setName(name);
                category.setId(globalcategoryId + 1);

                categoryId = globalcategoryId + 1;

                //Exiting category, but different from initial one
            } else if (initialCategoryID != checkedCategoryID) {
                category.setName(name);
                category.setId(checkedCategoryID);

                categoryId = checkedCategoryID;

            //Same category
            } else {

                category.setName(name);
                category.setId(initialCategoryID);

                categoryId = initialCategoryID;

            }

        }


        repository.insertCategory(category);

        return categoryId;

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