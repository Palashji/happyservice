package com.bluewebspark.happyservice.data.datahelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bluewebspark.happyservice.data.DataManager;
import com.bluewebspark.happyservice.data.model.UserDataModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.ArrayList;

/**
 * Created by androidsys1 on 8/12/2017.
 */

public class UserDataHelper {
    private static UserDataHelper instance;
    private SQLiteDatabase db;
    private DataManager dm;
    Context cx;

    public UserDataHelper(Context cx) {
        instance = this;
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }

    private boolean isExist(UserDataModel userDataModel) {
        read();
        Cursor cur = db.rawQuery("select * from " + UserDataModel.TABLE_NAME + " where " + UserDataModel.KEY_USER_ID + "='" + userDataModel.getUserId() + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }

    public static UserDataHelper getInstance() {
        return instance;
    }

    public void open() {
        db = dm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void read() {
        db = dm.getReadableDatabase();
    }

    public void insertUserDataModel(UserDataModel userDataModel) {
        open();
        ContentValues values = new ContentValues();

        values.put(UserDataModel.KEY_USER_ID, userDataModel.getUserId());
        values.put(UserDataModel.KEY_USER_NAME, userDataModel.getUserName());
        values.put(UserDataModel.KEY_EMAIL, userDataModel.getUserEmail());
        values.put(UserDataModel.KEY_USER_PHONE, userDataModel.getUserPhone());
        values.put(UserDataModel.KEY_USER_ALT_PHONE, userDataModel.getUserAlternatePhone());
        values.put(UserDataModel.KEY_USER_TYPE, userDataModel.getUserType());
        values.put(UserDataModel.KEY_IMAGE, userDataModel.getUserImage());
        values.put(UserDataModel.KEY_ADDRESS, userDataModel.getUserAddress());
        values.put(UserDataModel.KEY_CITY, userDataModel.getUserCity());
        values.put(UserDataModel.KEY_LAT, userDataModel.getUserLat());
        values.put(UserDataModel.KEY_LNG, userDataModel.getUserLong());
        values.put(UserDataModel.KEY_BY_SOCIAL, userDataModel.getUserBySocial());
        values.put(UserDataModel.KEY_FCM_ID, userDataModel.getFcm_id());
        values.put(UserDataModel.KEY_USER_STATUS, userDataModel.getUserStatus());

        if (!isExist(userDataModel)) {
            S.E("insert successfully");
            db.insert(UserDataModel.TABLE_NAME, null, values);
        } else {
            S.E("update successfully" + userDataModel.getUserId());
            db.update(UserDataModel.TABLE_NAME, values, UserDataModel.KEY_USER_ID + "=" + userDataModel.getUserId(), null);
        }
        close();
    }

    public ArrayList<UserDataModel> getUserDataModel() {
        ArrayList<UserDataModel> userItem = new ArrayList<UserDataModel>();
        read();
        Cursor clientCur = db.rawQuery("select * from " + UserDataModel.TABLE_NAME, null);

        if (clientCur != null && clientCur.getCount() > 0) {
            clientCur.moveToFirst();
            do {
                UserDataModel userDataModel = new UserDataModel();
                userDataModel.setUserId(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_USER_ID)));
                userDataModel.setUserName(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_USER_NAME)));
                userDataModel.setUserEmail(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_EMAIL)));
                userDataModel.setUserPhone(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_USER_PHONE)));
                userDataModel.setUserAlternatePhone(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_USER_ALT_PHONE)));
                userDataModel.setUserType(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_USER_TYPE)));
                userDataModel.setUserImage(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_IMAGE)));
                userDataModel.setUserAddress(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_ADDRESS)));
                userDataModel.setUserCity(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_CITY)));
                userDataModel.setUserLat(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_LAT)));
                userDataModel.setUserLong(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_LNG)));
                userDataModel.setUserBySocial(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_BY_SOCIAL)));
                userDataModel.setFcm_id(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_FCM_ID)));
                userDataModel.setUserStatus(clientCur.getString(clientCur.getColumnIndex(UserDataModel.KEY_USER_STATUS)));
                userItem.add(userDataModel);
            } while ((clientCur.moveToNext()));
            clientCur.close();
        }
        close();
        return userItem;
    }

    public void delete() {
        open();
        db.delete(UserDataModel.TABLE_NAME, null, null);
        close();
    }
}