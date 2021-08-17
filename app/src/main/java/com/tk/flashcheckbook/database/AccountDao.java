package com.tk.flashcheckbook.database;

import android.widget.Adapter;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(Account account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAccounts(List<Account> accounts);

    @Delete
    void deleteAccount(Account account);

    @Query("SELECT * FROM `account` WHERE id = :id")
    Account getAccountByID(int id);

    @Query("SELECT * FROM `account` ORDER BY name DESC")
    LiveData<List<Account>> getAllAccounts();

    @Query("DELETE FROM `account`")
    int deleteAllAccounts();

    @Query("SELECT COUNT(*) FROM `account`")
    int getAccountCount();

    @Query("SELECT name FROM 'account' ORDER BY name DESC")
    String[] getAccountNames();
}
