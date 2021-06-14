package com.tk.flashcheckbook.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "payee", foreignKeys = @ForeignKey(entity = Transaction.class, parentColumns = "id", childColumns = "id"))

public class Payee {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int categoryId;
    private String name;

    public Payee(int id, int categoryId, String name) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
    }

    @Ignore
    public Payee(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    @Ignore
    public Payee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
