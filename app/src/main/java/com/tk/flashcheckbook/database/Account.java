package com.tk.flashcheckbook.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "account")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int startBal;
    private Date startDate;


    public Account(int id, String name, int startBal, Date startDate) {
        this.id = id;
        this.name = name;
        this.startBal = startBal;
        this.startDate = startDate;
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

    public int getStartBal() {
        return startBal;
    }

    public void setStartBal(int startBal) {
        this.startBal = startBal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
