package com.bluewebspark.happyservice.model;

/**
 * Created by Sohel Khan on 18-Apr-18.
 */

public class ServiceVariationModel {

    /**
     * variationName : Up to 42 inch
     * priceType : 350
     * variationPrice : onwardPriceVar
     */

    private String variationName;
    private String priceType;
    private String variationPrice;

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getVariationPrice() {
        return variationPrice;
    }

    public void setVariationPrice(String variationPrice) {
        this.variationPrice = variationPrice;
    }
}
