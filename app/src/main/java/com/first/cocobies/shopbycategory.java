package com.first.cocobies;

public class shopbycategory {

    public String txt;
    public int img;

    public shopbycategory(String new_arrivals, int img) {
        this.txt=new_arrivals;
        this.img=img;
    }

    public String getTxt() {
        return txt;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
