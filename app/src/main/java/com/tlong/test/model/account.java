package com.tlong.test.model;

import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;
import java.util.Date;

public class account implements Serializable {
    private String username;
    private String pass;
    private int idaccount;
    private String role;
    private Date BDateTime;
    private byte[] img;

    public account(){}

    public account(String username, String pass, int idaccount, String role, Date BDateTime, byte[] img) {
        this.username = username;
        this.pass = pass;
        this.idaccount = idaccount;
        this.role = role;
        this.BDateTime = BDateTime;
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIdaccount() {
        return idaccount;
    }

    public void setIdaccount(int idaccount) {
        this.idaccount = idaccount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getBDateTime() {
        return BDateTime;
    }

    public void setBDateTime(Date BDateTime) {
        this.BDateTime = BDateTime;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
