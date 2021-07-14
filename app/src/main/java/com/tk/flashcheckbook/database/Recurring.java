package com.tk.flashcheckbook.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "recurring")


public class Recurring {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private int accountId;
    private BigDecimal amount;
    private Date startDate;
    private Date clearedDate;
    private int repeatInterval;
    private int payeeId;
    private int categoryId;
    private String number;
    private String note;
    private int cleared;
    private int recurring;


    public Recurring(int id, int accountId, BigDecimal amount, Date startDate, Date clearedDate, int repeatInterval, int payeeId, int categoryId, String number, String note, int cleared, int recurring) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.startDate = startDate;
        this.clearedDate = clearedDate;
        this.repeatInterval = repeatInterval;
        this.payeeId = payeeId;
        this.categoryId = categoryId;
        this.number = number;
        this.note = note;
        this.cleared = cleared;
        this.recurring = recurring;
    }

    public Recurring(int accountId, BigDecimal amount, Date startDate, Date clearedDate, int repeatInterval, int payeeId, int categoryId, String number, String note, int cleared, int recurring) {
        this.accountId = accountId;
        this.amount = amount;
        this.startDate = startDate;
        this.clearedDate = clearedDate;
        this.repeatInterval = repeatInterval;
        this.payeeId = payeeId;
        this.categoryId = categoryId;
        this.number = number;
        this.note = note;
        this.cleared = cleared;
        this.recurring = recurring;
    }

    public Recurring() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getClearedDate() {
        return clearedDate;
    }

    public void setClearedDate(Date clearedDate) {
        this.clearedDate = clearedDate;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public int getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(int payeeId) {
        this.payeeId = payeeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCleared() {
        return cleared;
    }

    public void setCleared(int cleared) {
        this.cleared = cleared;
    }

    public int getRecurring() {
        return recurring;
    }

    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }
}
