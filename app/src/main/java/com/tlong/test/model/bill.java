package com.tlong.test.model;

import java.io.Serializable;
import java.util.Date;

public class bill implements Serializable {
    private int IdBill;
    private int IdCust;
    private Date BDateTime;
    private double Total;
    private String Payment;
    private String Messenger;

    public bill(int idBill, int idCust, Date BDateTime, double total, String payment, String messenger) {
        IdBill = idBill;
        IdCust = idCust;
        this.BDateTime = BDateTime;
        Total = total;
        Payment = payment;
        Messenger = messenger;
    }

    public int getIdBill() {
        return IdBill;
    }

    public void setIdBill(int idBill) {
        IdBill = idBill;
    }

    public int getIdCust() {
        return IdCust;
    }

    public void setIdCust(int idCust) {
        IdCust = idCust;
    }

    public Date getBDateTime() {
        return BDateTime;
    }

    public void setBDateTime(Date BDateTime) {
        this.BDateTime = BDateTime;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getMessenger() {
        return Messenger;
    }

    public void setMessenger(String messenger) {
        Messenger = messenger;
    }
}
