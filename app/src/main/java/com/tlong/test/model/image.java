package com.tlong.test.model;

import java.io.Serializable;

public class image  implements Serializable {
    int IdImage;
    String Image;
    int Idproduct;

    public image(int idImage, String image, int idproduct) {
        IdImage = idImage;
        Image = image;
        Idproduct = idproduct;
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

    public int getIdproduct() {
        return Idproduct;
    }

    public void setIdproduct(int idproduct) {
        Idproduct = idproduct;
    }
}
