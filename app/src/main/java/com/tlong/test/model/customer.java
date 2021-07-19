package com.tlong.test.model;

import java.io.Serializable;
import java.util.Date;

public class customer implements Serializable {
    private int IdPro;
    private String Name;
    private Date ngay;
    private String gender;
    private String email;
    private String phone;

    public customer(int idPro, String name, Date ngay, String gender, String email, String phone) {
        IdPro = idPro;
        Name = name;
        this.ngay = ngay;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public int getIdPro() {
        return IdPro;
    }

    public void setIdPro(int idPro) {
        IdPro = idPro;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
