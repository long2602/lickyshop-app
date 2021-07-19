package com.tlong.test.model;

import java.io.Serializable;

public class search implements Serializable{
    private int IdProduct;
    private String Name;
    private String Imei;
    private String Description;
    private int IdType;
    private double price;
    private int IdImage;
    private String Image;

    public search(int idProduct, String name, String imei, String description, int idType, double price, int idImage, String image) {
        IdProduct = idProduct;
        Name = name;
        Imei = imei;
        Description = description;
        IdType = idType;
        this.price = price;
        IdImage = idImage;
        Image = image;
    }

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

    public int getIdImage() {
        return IdImage;
    }

    public void setIdImage(int idImage) {
        IdImage = idImage;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
