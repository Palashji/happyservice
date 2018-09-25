package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 22-Mar-18.
 */

public class OpenModel {

    /**
     * requestID : 4
     * servicePrice : 450
     * serviceName : Dry Servicing
     * approximateTime : 1 Hour(s)
     * serviceImage : e2e-dry-servicing.jpg
     * customerStatus : 3
     */

    private String requestID;
    private String servicePrice;
    private String serviceName;
    private String approximateTime;
    private String serviceImage;
    private String customerStatus;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
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

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
}
