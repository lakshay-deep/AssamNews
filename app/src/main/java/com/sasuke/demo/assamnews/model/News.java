package com.sasuke.demo.assamnews.model;

public class News {
    private String Title;
    private int Logo;

    public News(){}

    public News(String title, int logo) {
        Title = title;
        Logo = logo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getLogo() {
        return Logo;
    }

    public void setLogo(int logo) {
        Logo = logo;
    }
}
