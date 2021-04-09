package com.tk.flashcheckbook.model;

public class Payee {

    private int id;
    private String name;

    public Payee(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
