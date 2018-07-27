package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 07-Mar-18.
 */

public class SubCategoriesModel {

    /**
     * subCatID : 2
     * subCatName : TV
     * catID : 14
     */

    private String subCatID;
    private String subCatName;
    private String catID;

    public String getSubCatID() {
        return subCatID;
    }

    public void setSubCatID(String subCatID) {
        this.subCatID = subCatID;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }
}
