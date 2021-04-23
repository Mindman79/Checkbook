package com.tk.flashcheckbook.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "payee")
public class Payee {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Payee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Payee(String name) {
        this.name = name;
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
