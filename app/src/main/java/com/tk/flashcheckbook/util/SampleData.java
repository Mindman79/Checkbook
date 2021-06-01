package com.tk.flashcheckbook.util;

import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleData {

    public static List<Payee> getTestPayees() {

        List<Payee> payees = new ArrayList<>();

        payees.add(new Payee(1, "McDonald's"));
        payees.add(new Payee(2, "Arby's"));


        return payees;
    }


    public static List<Category> getTestCategories() {

        List<Category> categories = new ArrayList<>();

        categories.add(new Category("Merchandise"));
        categories.add(new Category("Eating Out"));


        return categories;
    }

    public static List<Transaction> getTestTransactions() {





        List<Transaction> transactions = new ArrayList<>();



        transactions.add(new Transaction(BigDecimal.valueOf(333.40), new Date(2019, 12, 5), new Date(2019, 12, 5), 1, 1, 1, "Note1", 1));
        transactions.add(new Transaction(BigDecimal.valueOf(188.60), new Date(2018, 7, 5), new Date(2018, 7, 5), 2, 2, 1, "Note asdhjkasd", 1));

        return transactions;

    }




}
