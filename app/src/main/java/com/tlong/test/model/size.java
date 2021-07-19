package com.tlong.test.model;

import java.io.Serializable;

public class size implements Serializable {
    private int IdSize;
    private String Stitle;
    private int Amount;
    private int IdPro;

    public size(int idSize, String stitle, int amount, int idPro) {
        IdSize = idSize;
        Stitle = stitle;
        Amount = amount;
        IdPro = idPro;
    }

    public int getIdSize() {
        return IdSize;
    }

    public void setIdSize(int idSize) {
        IdSize = idSize;
    }

    public String getStitle() {
        return Stitle;
    }

    public void setStitle(String stitle) {
        Stitle = stitle;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getIdPro() {
        return IdPro;
    }

    public void setIdPro(int idPro) {
        IdPro = idPro;
    }
}
