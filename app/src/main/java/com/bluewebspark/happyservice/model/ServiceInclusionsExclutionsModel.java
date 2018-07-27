package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 21-Mar-18.
 */

public class ServiceInclusionsExclutionsModel {
    String inclutionsExclutionsId;
    String inclutionsExclutionsTitle;

    public ServiceInclusionsExclutionsModel(String inclutionsExclutionsId, String inclutionsExclutionsTitle) {
        this.inclutionsExclutionsId = inclutionsExclutionsId;
        this.inclutionsExclutionsTitle = inclutionsExclutionsTitle;
    }

    public String getInclutionsExclutionsId() {
        return inclutionsExclutionsId;
    }

    public void setInclutionsExclutionsId(String inclutionsExclutionsId) {
        this.inclutionsExclutionsId = inclutionsExclutionsId;
    }

    public String getInclutionsExclutionsTitle() {
        return inclutionsExclutionsTitle;
    }

    public void setInclutionsExclutionsTitle(String inclutionsExclutionsTitle) {
        this.inclutionsExclutionsTitle = inclutionsExclutionsTitle;
    }
}
