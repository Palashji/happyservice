package com.bluewebspark.happyservice.model;

/**
 * Created by Sohel Khan on 29-Mar-18.
 */

public class BookingsVendorModel {

    /**
     * vendorID : 51
     * vendorName : sohel khan
     * vendorImage : http://bwsproduction.com/happyservice/adminDashboard/vendors_image/51/userProfile1524819185.jpg
     * vendorStatus : 2
     */

    private String vendorID;
    private String vendorName;
    private String vendorImage;
    private String vendorStatus;

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorImage() {
        return vendorImage;
    }

    public void setVendorImage(String vendorImage) {
        this.vendorImage = vendorImage;
    }

    public String getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(String vendorStatus) {
        this.vendorStatus = vendorStatus;
    }
}
