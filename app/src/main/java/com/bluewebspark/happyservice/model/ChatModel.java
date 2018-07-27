package com.bluewebspark.happyservice.model;

/**
 * Created by Sohel Khan on 29-Mar-18.
 */

public class ChatModel {
    /**
     * serviceName : Carpet Shampooing
     * serviceRequestID : 1
     * userName : Sohel Khan Partner
     * messageBY : 60
     * userImage : http://bwsproduction.com/happyservice/adminDashboard/vendors_image/60/userProfile1525156124.jpg
     */

    private String serviceName;
    private String serviceRequestID;
    private String userName;
    private String messageBY;
    private String userImage;
    private String requestStatus;

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceRequestID() {
        return serviceRequestID;
    }

    public void setServiceRequestID(String serviceRequestID) {
        this.serviceRequestID = serviceRequestID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageBY() {
        return messageBY;
    }

    public void setMessageBY(String messageBY) {
        this.messageBY = messageBY;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
