package com.tk.flashcheckbook.util;

import com.tk.flashcheckbook.model.Category;
import com.tk.flashcheckbook.model.Payee;
import com.tk.flashcheckbook.model.Transaction;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleData {

    public static List<Transaction> getTestTransactions() {





        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.valueOf(20.40), new Date(2019, 12, 5), new Payee("McDonald's"), new Category("Eating Out"), 1, "It was delish!", true));
        transactions.add(new Transaction(2, BigDecimal.valueOf(10.00), new Date(2018, 7, 5), new Payee("Arby's"), new Category("Eating Out"), 0, "It was delish!", true));
        transactions.add(new Transaction(3, BigDecimal.valueOf(1.50), new Date(2017, 1, 5), new Payee("Wendy's"), new Category("Eating Out"), 55, "It was delish!", false));



        return transactions;

    }


//    public static LocalDateTime dateTimeConverter(LocalDateTime ldt) {
//
//
//
//        String convertedDateTime = ldt.toString()w
//
//    }
//    LocalDateTime currentDateTime = LocalDateTime.parse(LocalDateTime.now(), DateTimeFormatter);
//        currentDateTime.parse()
//    SimpleDateFormat hs = new SimpleDateFormat("dd/mm/YYYY");


}
