package com.bluewebspark.happyservice.model;

/**
 * Created by Sohel Khan on 02-Apr-18.
 */

public class ReviewModel {

    /**
     * reviewBy : 64
     * ratingNumber : 2
     * reviewComment : good
     * vendorName : Sohel CT
     * vendorImage : http://bwsproduction.com/happyservice/adminDashboard/vendors_image/64/userProfile1525178391.jpg
     */

    private String reviewBy;
    private String ratingNumber;
    private String reviewComment;
    private String vendorName;
    private String vendorImage;

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public String getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(String ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
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
}
