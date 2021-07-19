package com.tlong.test.model;

import java.io.Serializable;

public class product implements Serializable {
    public int IdProduct;
    public String Name;
    public String Imei;
    public String Description;
    public int IdType;
    public double price;

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public product(int idProduct, String name, String imei, String description, int idType, double price) {
        IdProduct = idProduct;
        Name = name;
        Imei = imei;
        Description = description;
        IdType = idType;
        this.price = price;
    }
}
