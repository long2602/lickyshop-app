package com.tlong.test.model;

import java.io.Serializable;

public class billdetail  implements Serializable {
    int IdBillDetail;
    int IdBill;
    int Amount;
    double Price;
    double Total;
    int IdPro;

    public billdetail(int idBillDetail, int idBill, int amount, double price, double total, int idPro) {
        IdBillDetail = idBillDetail;
        IdBill = idBill;
        Amount = amount;
        Price = price;
        Total = total;
        IdPro = idPro;
    }

    public int getIdBillDetail() {
        return IdBillDetail;
    }

    public void setIdBillDetail(int idBillDetail) {
        IdBillDetail = idBillDetail;
    }

    public int getIdBill() {
        return IdBill;
    }

    public void setIdBill(int idBill) {
        IdBill = idBill;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public int getIdPro() {
        return IdPro;
    }

    public void setIdPro(int idPro) {
        IdPro = idPro;
    }
}
