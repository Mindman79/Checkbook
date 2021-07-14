package com.tk.flashcheckbook.database;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "payperiod")


public class PayPeriod {


    private int id;
    private int accountId;
    private String name;
    private Date startDate;
    private Date endDate;
    private int repeatInterval;

    public PayPeriod(int id, int accountId, String name, Date startDate, Date endDate, int repeatInterval) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeatInterval = repeatInterval;
    }

    public PayPeriod(int accountId, String name, Date startDate, Date endDate, int repeatInterval) {
        this.accountId = accountId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeatInterval = repeatInterval;
    }

    public PayPeriod() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
