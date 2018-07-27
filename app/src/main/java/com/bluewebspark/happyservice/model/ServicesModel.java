package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 21-Mar-18.
 */

public class ServicesModel {

    /**
     * serviceID : 4
     * serviceName : Carpet Shampooing
     * serviceImage : e2e-dry-servicing.jpg
     * serviceCategoryID : 2
     */

    private String serviceID;
    private String serviceName;
    private String serviceImage;
    private String serviceCategoryID;

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceCategoryID() {
        return serviceCategoryID;
    }

    public void setServiceCategoryID(String serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }
}
