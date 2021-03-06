package com.tk.flashcheckbook.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.math.BigDecimal;
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

    @Query("SELECT * FROM `transaction` where accountId = :id ORDER BY date DESC")
    List<Transaction> getAllTransactionsByAccountID(int id);

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("DELETE FROM `transaction`")
    int deleteAllTransactions();

    @Query("SELECT COUNT(*) FROM `transaction`")
    int getTransactionCount();

    @Query("SELECT IFNULL(SUM(amount), 0) FROM `transaction`")
    BigDecimal getTotalofAllTransactions();

    @Query("SELECT IFNULL(SUM(amount), 0) FROM `transaction` WHERE cleared = 1")
    BigDecimal getTotalofClearedTransactions();

    @Query("SELECT IFNULL(SUM(amount), 0) FROM `transaction` WHERE accountId = :accountId")
    BigDecimal getTotalofAllTransactionsByAccountId(int accountId);

    @Query("SELECT IFNULL(SUM(amount), 0) FROM `transaction` WHERE accountId = :accountId AND cleared = 1")
    BigDecimal getTotalofAllClearedTransactionsByAccountId(int accountId);
}
