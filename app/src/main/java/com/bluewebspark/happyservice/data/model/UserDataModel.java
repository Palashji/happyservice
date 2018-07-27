package com.bluewebspark.happyservice.data.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sohel Khan on 8/12/2017.
 */

public class UserDataModel {
    public static final String TABLE_NAME = "user";
    public static final String KEY_ID = "_id";
    public static final String KEY_USER_ID = "ID";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_EMAIL = "userEmail";
    public static final String KEY_USER_PHONE = "userPhone";
    public static final String KEY_USER_ALT_PHONE= "userAlternatePhone";
    public static final String KEY_USER_TYPE = "userType";
    public static final String KEY_IMAGE = "userImage";
    public static final String KEY_ADDRESS = "userAddress";
    public static final String KEY_CITY = "userCity";
    public static final String KEY_LAT = "userLat";
    public static final String KEY_LNG = "userLong";
    public static final String KEY_BY_SOCIAL = "userBySocial";
    public static final String KEY_FCM_ID = "userfirbaseID";
    public static final String KEY_USER_STATUS = "userStatus";

    public static void creteTable(SQLiteDatabase db) {
        String CREATE_STUDENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " text, "
                + KEY_USER_NAME + " text, "
                + KEY_EMAIL + " text, "
                + KEY_USER_PHONE + " text, "
                + KEY_USER_ALT_PHONE + " text, "
                + KEY_USER_TYPE + " text, "
                + KEY_IMAGE + " text, "
                + KEY_ADDRESS + " text, "
                + KEY_CITY + " text, "
                + KEY_LAT + " text, "
                + KEY_LNG + " text, "
                + KEY_BY_SOCIAL + " text, "
                + KEY_FCM_ID + " text, "
                + KEY_USER_STATUS + " text " +
                ")";
        db.execSQL(CREATE_STUDENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    String userId, userName, userEmail, userPhone, userType, userImage, userAddress, userCity, userLat, userLong, userBySocial, fcm_id, userStatus,userAlternatePhone;

    public String getUserAlternatePhone() {
        return userAlternatePhone;
    }

    public void setUserAlternatePhone(String userAlternatePhone) {
        this.userAlternatePhone = userAlternatePhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }

    public String getUserBySocial() {
        return userBySocial;
    }

    public void setUserBySocial(String userBySocial) {
        this.userBySocial = userBySocial;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
