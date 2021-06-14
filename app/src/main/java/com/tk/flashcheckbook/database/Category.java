package com.tk.flashcheckbook.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category", foreignKeys = @ForeignKey(entity = Payee.class, parentColumns = "id", childColumns = "id"))

public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Category(String name) {
        this.name = name;
    }


    @Ignore
    public Category() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
