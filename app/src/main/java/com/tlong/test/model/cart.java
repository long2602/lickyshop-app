package com.tlong.test.model;

import java.io.Serializable;

public class cart implements Serializable {
    public int IdPro;
    public String namePro;
    public double price;
    public String img;
    public int amount;
    public double total;

    public cart(int idPro, String namePro, double price, String img, int amount, double total) {
        IdPro = idPro;
        this.namePro = namePro;
        this.price = price;
        this.img = img;
        this.amount = amount;
        this.total = total;
    }

    public int getIdPro() {
        return IdPro;
    }

    public void setIdPro(int idPro) {
        IdPro = idPro;
    }

    public String getNamePro() {
        return namePro;
    }

    public void setNamePro(String namePro) {
        this.namePro = namePro;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
