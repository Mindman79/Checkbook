package com.tk.flashcheckbook.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategories(List<Category> categories);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM `category` WHERE id = :id")
    Category getCategoryById(int id);

    @Query("SELECT * FROM `category` ORDER BY name desc")
    LiveData<List<Category>> getAllCategories();

    @Query("DELETE FROM `category`")
    int deleteAllCategories();

    @Query("SELECT COUNT(*) FROM `category`")
    int getCategoryCount();
//
//    @Query("SELECT AUTO_INCREMENT\n" +
//            "FROM information_schema.TABLES\n" +
//            "WHERE TABLE_SCHEMA = 'AppDatabase'\n" +
//            "AND TABLE_NAME = 'category'")
//    int getNextAutoIncrementCategoryID();


//    @Query("SELECT last_insert_rowid() FROM category")
//    int getLastAutoIncrementCategoryID();

    @Query("SELECT * FROM SQLITE_SEQUENCE WHERE name = 'category'")
    int getNextAutoIncrementCategoryID();
}
