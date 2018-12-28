package com.example.lenovo.newclassmate.Bean;

import android.widget.ImageView;
import android.widget.TextView;

public class Group {

    int imag;
    String text;
    int img;

    public Group(int img,String text,int imag) {
        this.img = img;
        this.text = text;
        this.imag= imag;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIamg(){
        return imag;
    }
    public  void  setImag(){
        this.imag= imag;
    }
}
