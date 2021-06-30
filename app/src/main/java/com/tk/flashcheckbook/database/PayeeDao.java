package com.tk.flashcheckbook.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;

import java.util.List;

@Dao
public interface PayeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPayee(Payee payee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPayees(List<Payee> payees);

    @Delete
    void deletePayee(Payee payee);

    @Query("SELECT * FROM `payee` WHERE id = :id")
    Payee getPayeeById(int id);

    @Query("SELECT * FROM `payee`")
    LiveData<List<Payee>> getAllPayees();

    @Query("DELETE FROM `payee`")
    int deleteAllPayees();

    @Query("SELECT COUNT(*) FROM `payee`")
    int getPayeeCount();

    @Query("SELECT seq FROM SQLITE_SEQUENCE WHERE name = 'payee'")
    int getNextAutoIncrementPayeeID();

    @Query("SELECT name FROM `payee` WHERE name LIKE :name")
    LiveData<List<String>> getAllPayeesByName(String name);



}
