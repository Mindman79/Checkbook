package com.tk.flashcheckbook.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

    @Query("SELECT * FROM `payee` ORDER BY name desc")
    LiveData<List<Payee>> getAllPayees();

    @Query("DELETE FROM `payee`")
    int deleteAllPayees();

    @Query("SELECT COUNT(*) FROM `payee`")
    int getPayeeCount();

    //TODO: resume here
//    @Query("SELECT AUTO_INCREMENT\n" +
//            "FROM information_schema.TABLES\n" +
//            "WHERE TABLE_SCHEMA = 'AppDatabase'\n" +
//            "AND TABLE_NAME = 'payee'")
//    int getNextAutoIncrementPayeeID();

    @Query("SELECT * FROM SQLITE_SEQUENCE WHERE name = 'payee'")
    int getNextAutoIncrementPayeeID();

//    @Query("SELECT last_insert_rowid() FROM payee")
//    int getLastAutoIncrementCategoryID();





}
