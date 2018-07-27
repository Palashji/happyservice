package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 22-Mar-18.
 */

public class OnGoingModel {

    /**
     * requestID : 2
     * servicePrice : null
     * serviceName : Dry Servicing
     * approximateTime : 1 Hour(s)
     * serviceImage : e2e-dry-servicing.jpg
     */

    private String requestID;
    private Object servicePrice;
    private String serviceName;
    private String approximateTime;
    private String serviceImage;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Object getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Object servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApproximateTime() {
        return approximateTime;
    }

    public void setApproximateTime(String approximateTime) {
        this.approximateTime = approximateTime;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }
}
