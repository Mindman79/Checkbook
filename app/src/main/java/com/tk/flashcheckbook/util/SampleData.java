package com.tk.flashcheckbook.util;

import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.logging.Logger.global;

public class SampleData {

    private static AppRepository repository;
    static int globalPayeeId;
    static int globalCategoryId;


    public static List<Payee> getTestPayees() {

//        globalPayeeId = repository.getNextAutoIncrementPayeeID();
//        globalCategoryId = repository.getNextAutoIncrementCategoryID();
//
//        int payeeId = globalPayeeId;
//        int categoryId = globalCategoryId;

        List<Payee> payees = new ArrayList<>();

        payees.add(new Payee(1, 1, "McDonald's"));
        payees.add(new Payee(2, 2, "Arby's"));

        return payees;
    }


    public static List<Category> getTestCategories() {


//        globalCategoryId = repository.getNextAutoIncrementCategoryID();

        //int categoryId = globalCategoryId;

        List<Category> categories = new ArrayList<>();

        categories.add(new Category("Merchandise"));
        categories.add(new Category("Eating Out"));


        return categories;
    }

    public static List<Transaction> getTestTransactions() {

//        globalPayeeId = repository.getNextAutoIncrementPayeeID();
//        globalCategoryId = repository.getNextAutoIncrementCategoryID();
//
//        int payeeId = globalPayeeId;
//        int categoryId = globalCategoryId;

        List<Transaction> transactions = new ArrayList<>();


        transactions.add(new Transaction(BigDecimal.valueOf(333.40), new Date(2019, 12, 5), new Date(2019, 12, 5), 1, 1, "1", "Note1", 1));
        transactions.add(new Transaction(BigDecimal.valueOf(188.60), new Date(2018, 7, 5), new Date(2018, 7, 5), 2, 2, "1", "Note asdhjkasd", 1));

        return transactions;





    }


    public void getNextIDs() {


        globalPayeeId = repository.getNextAutoIncrementPayeeID();


        globalCategoryId = repository.getNextAutoIncrementCategoryID();

    }



}
