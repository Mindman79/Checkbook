package com.tk.flashcheckbook.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTransactions(List<Transaction> transactionsList);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    Transaction getTransactionById(int id);

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("DELETE FROM `transaction`")
    int deleteAllTransactions();

    @Query("SELECT COUNT(*) FROM `transaction`")
    int getTransactionCount();



}
