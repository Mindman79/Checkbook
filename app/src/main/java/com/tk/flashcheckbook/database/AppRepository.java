package com.tk.flashcheckbook.database;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.LiveData;

import com.tk.flashcheckbook.util.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {


    private static AppRepository ourInstance;


    public LiveData<List<Transaction>> transactionList;
    public LiveData<List<Payee>> payeeList;
    public LiveData<List<Category>> categoryList;
    private AppDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {

        if (ourInstance == null) {

            ourInstance = new AppRepository(context);
        }

        return  ourInstance;
    }

    private AppRepository(Context context) {

        db = AppDatabase.getInstance(context);
        transactionList = getAllTransactions();
        payeeList = getAllPayees();
        categoryList = getAllCategories();


    }


    public void addSampleData() {


        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.transactionDao().insertAllTransactions(SampleData.getTestTransactions());
                db.payeeDao().insertAllPayees(SampleData.getTestPayees());
                db.categoryDao().insertAllCategories(SampleData.getTestCategories());
            }
        });

    }

    //Method that determines if the data is local or remote
    private LiveData<List<Transaction>> getAllTransactions() {

        return db.transactionDao().getAllTransactions();

    }

    private LiveData<List<Payee>> getAllPayees() {

        return db.payeeDao().getAllPayees();

    }

    private LiveData<List<Category>> getAllCategories() {

        return db.categoryDao().getAllCategories();

    }

    public void deleteAllNotes() {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.transactionDao().deleteAllTransactions();

            }
        });

    }

    public Transaction getTransById(int transId) {

        return db.transactionDao().getTransactionById(transId);

    }

    public void insertTransaction(final Transaction transaction) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.transactionDao(). insertTransaction(transaction);
            }
        });

    }

    public Payee getPayeeById(int payeeId) {

        return db.payeeDao().getPayeeById(payeeId);

    }

    public void insertPayee(final Payee payee) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.payeeDao(). insertPayee(payee);
            }
        });

    }

    public Category getCategoryById(int categoryId) {

        return db.categoryDao().getCategoryById(categoryId);

    }

    public void insertCategory(final Category category) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.categoryDao().insertCategory(category);
            }
        });



    }




    public int getNextAutoIncrementPayeeID() {

        final int[] result = {0};
        executor.execute(new Runnable() {
            @Override
            public void run() {

                result[0] = db.payeeDao().getNextAutoIncrementPayeeID();
            }
        });

        int test = result[0];
        return test;

    }



    public int getNextAutoIncrementCategoryID() {

        final int[] result = {0};
        executor.execute(new Runnable() {
            @Override
            public void run() {

                result[0] = db.categoryDao().getNextAutoIncrementCategoryID();
            }
        });

       int test = result[0];
       return test;

    }



    public void printAutoIncrements(){
        String query = "SELECT * FROM SQLITE_SEQUENCE";
        Cursor cursor = db.query(query, null);
        if (cursor.moveToFirst()){
            do{
                System.out.println("tableName: " +cursor.getString(cursor.getColumnIndex("payee")));
                System.out.println("autoInc: " + cursor.getString(cursor.getColumnIndex("category")));

            }while (cursor.moveToNext());
        }

        cursor.close();

    }

}


