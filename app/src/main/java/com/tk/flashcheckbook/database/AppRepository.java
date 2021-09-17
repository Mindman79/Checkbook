package com.tk.flashcheckbook.database;

//import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.util.Globals;
import com.tk.flashcheckbook.util.SampleData;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


//TODO: See if this extends is needed
public class AppRepository extends AppCompatActivity {


//    private final String MY_PREFS = "myPrefs";
//    SharedPreferences preferences = getSharedPreferences(MY_PREFS,Context.MODE_PRIVATE);
//
//
//    int defaultValue = getResources().getInteger(R.integer.selected_account_id_default_key);
//    int accountId = preferences.getInt(getString(R.integer.selected_account_id), defaultValue);

    private static AppRepository ourInstance;

    public MutableLiveData<List<Payee>> payeeList;
    public MutableLiveData<List<Category>> categoryList;
    public MutableLiveData<List<Account>> accountList;
    public LiveData<List<PayPeriod>> payPeriodList;
    public LiveData<List<Recurring>> recurringList;

    private AppDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();
    public int payeeId;
    public int categoryId;


    public static AppRepository getInstance(Context context) {

        if (ourInstance == null) {

            ourInstance = new AppRepository(context);
        }

        return ourInstance;
    }

    public AppRepository(Context context) {

        db = AppDatabase.getInstance(context);


        payeeList = getAllPayees();
        categoryList = getAllCategories();
        accountList = getAllAccounts();


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
    public MutableLiveData<List<Transaction>> getAllTransactionsByAccountID(int id) {

        final MutableLiveData<List<Transaction>> data = new MutableLiveData<>();

        data.setValue(db.transactionDao().getAllTransactionsByAccountID(id));

        return data;

    }

    //Method that determines if the data is local or remote
    private LiveData<List<Transaction>> getAllTransactions() {

        return db.transactionDao().getAllTransactions();

    }


    public MutableLiveData<List<Payee>> getAllPayees() {

        final MutableLiveData<List<Payee>> data = new MutableLiveData<>();

        data.setValue(db.payeeDao().getAllPayees());

        return data;

    }

    private MutableLiveData<List<Category>> getAllCategories() {

        final MutableLiveData<List<Category>> data = new MutableLiveData<>();

        data.setValue(db.categoryDao().getAllCategories());

        return data;

    }

    public MutableLiveData<List<Account>> getAllAccounts() {

        final MutableLiveData<List<Account>> data = new MutableLiveData<>();

        data.setValue(db.accountDao().getAllAccounts());

        return data;

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

                db.transactionDao().insertTransaction(transaction);


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


                db.payeeDao().insertPayee(payee);


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


        payeeId = db.payeeDao().getNextAutoIncrementPayeeID();


        return payeeId;
    }


    public int getNextAutoIncrementCategoryID() {


        categoryId = db.categoryDao().getNextAutoIncrementCategoryID();


        return categoryId;
    }


    public void deleteTransaction(final Transaction transaction) {


        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.transactionDao().deleteTransaction(transaction);

            }
        });


    }

    public String[] getAllPayeesByName(String name) {

        String searchString = "%" + name + "%";

        return db.payeeDao().getAllPayeesByName(searchString);

    }

    public String[] getAllCategoriesByName(String name) {

        String searchString = "%" + name + "%";

        return db.categoryDao().getAllCategoriesByName(searchString);

    }

    public int getAssociatedCategoryIDByPayeeName(String name) {


        return db.payeeDao().getAssociatedCategoryIDByPayeeName(name);



    }

    public BigDecimal getTotalofClearedTransactions() {

        return db.transactionDao().getTotalofClearedTransactions();

    }

    public BigDecimal getTotalofAllTransactions() {


            return db.transactionDao().getTotalofAllTransactions();

    }

    public Account getAccountById(int accountId) {

        return db.accountDao().getAccountByID(accountId);

    }

    public void insertAccount(Account account) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.accountDao().insertAccount(account);

            }
        });


    }

    public void deleteAccount(Account account) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.accountDao().deleteAccount(account);

            }
        });

    }

    public int getAccountCount() {

        return db.accountDao().getAccountCount();

    }

    public String[] getAccountNames() {

        return db.accountDao().getAccountNames();
    }

    public Account getAccountByName(String name) {

        return db.accountDao().getAccountByName(name);

    }


    public BigDecimal getTotalofAllTransactionsByAccountId(int accountId) {

        return db.transactionDao().getTotalofAllTransactionsByAccountId(accountId);


    }

    public BigDecimal getTotalofAllClearedTransactionsByAccountId(int accountId) {

        return db.transactionDao().getTotalofAllClearedTransactionsByAccountId(accountId);

    }

    public void deletePayee(Payee value) {


        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.payeeDao().deletePayee(value);

            }
        });

    }
}


