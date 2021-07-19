package com.tlong.test.model;

import java.io.Serializable;
import java.net.PortUnreachableException;

public class type implements Serializable {
    public int IdType;
    public String Title;
    public type(String ten){
        Title=ten;
    }

    public type(int idType, String title) {
        IdType = idType;
        Title = title;
    }

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
