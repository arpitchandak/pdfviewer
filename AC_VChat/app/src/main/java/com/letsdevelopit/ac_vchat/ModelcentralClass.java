package com.letsdevelopit.ac_vchat;

public class ModelcentralClass {


    private String Name;
    private String URL;
    private String ImgUrl;


    public ModelcentralClass(String url,String imgUrl, String name) {

        this.URL = url;
        this.ImgUrl = imgUrl;
        this.Name = name;

    }

    public String getName() {
        return Name;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getURL() {
        return URL;
    }
}

