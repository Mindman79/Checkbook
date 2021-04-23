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

    @Query("SELECT * FROM `category` ORDER BY date desc")
    LiveData<List<Category>> getAllcategorys();

    @Query("DELETE FROM `category`")
    int deleteAllCategories();

    @Query("SELECT COUNT(*) FROM `category`")
    int getCategoryCount();


}
