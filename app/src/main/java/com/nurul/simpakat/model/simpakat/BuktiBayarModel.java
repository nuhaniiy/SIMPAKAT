package com.nurul.simpakat.model.simpakat;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

import lombok.Data;

@Data
public class BuktiBayarModel implements Serializable {
    private String nameBarang;
    private String nominal;
    private String pathBukti;
    private Bitmap imgFoto;

    public BuktiBayarModel() {
    }

    public String getNameBarang() {
        return nameBarang;
    }

    public void setNameBarang(String nameBarang) {
        this.nameBarang = nameBarang;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getPathBukti() {
        return pathBukti;
    }

    public void setPathBukti(String pathBukti) {
        this.pathBukti = pathBukti;
    }

    public Bitmap getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(Bitmap imgFoto) {
        this.imgFoto = imgFoto;
    }
}
