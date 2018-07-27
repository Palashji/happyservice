package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 07-Mar-18.
 */

public class CategoryModel {

    /**
     * catID : 14
     * catName : Appliance
     * catIcon : washing-machine.png
     */

    private String catID;
    private String catName;
    private String catIcon;

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }
}
