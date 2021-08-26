package com.tk.flashcheckbook.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "account")
public class Settings {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private BigDecimal startBal;
    private Date startDate;

    public Settings(int id, String name, BigDecimal startBal, Date startDate) {
        this.id = id;
        this.name = name;
        this.startBal = startBal;
        this.startDate = startDate;
    }

    @Ignore
    public Settings(String name, BigDecimal startBal, Date startDate) {
        this.name = name;
        this.startBal = startBal;
        this.startDate = startDate;
    }

    @Ignore
    public Settings() {
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

    public BigDecimal getStartBal() {
        return startBal;
    }

    public void setStartBal(BigDecimal startBal) {
        this.startBal = startBal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
