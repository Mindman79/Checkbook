package com.tk.flashcheckbook.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "transaction")

public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private BigDecimal amount;
    private Date date;
    private Payee payee;
    private Category category;
    private int number;
    private String note;
    private boolean cleared;

    public Transaction(int id, BigDecimal amount, Date date, Payee payee, Category category, int number, String note, boolean cleared) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.payee = payee;
        this.category = category;
        this.number = number;
        this.note = note;
        this.cleared = cleared;
    }

    @Ignore
    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }
}
