package com.bluewebspark.happyservice.sohel;

import android.app.Dialog;
import android.content.Context;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Sohel Khan on 12-Feb-18.
 */

public class JSONParser {
    private Context cx;

    // constructor
    public JSONParser(Context cx) {
        this.cx = cx;
    }

    public void parseRetrofitRequest(Call<ResponseBody> call, final Helper h) {
        final Dialog materialDialog = S.initProgressDialog(cx);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                materialDialog.dismiss();
                try {
                    S.E("prams : " + response.isSuccessful());
                    if (response.isSuccessful())
                        h.backResponse(response.body().string());
                    else
                        h.backResponse("error");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                materialDialog.dismiss();
                h.backResponse("error");
                S.E("sdcard-err2:" + t.toString());
                S.E("Something went wrong.!");
            }
        });
    }

    public void parseRetrofitRequestWithautProgress(Call<ResponseBody> call, final Helper h) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful())
                        h.backResponse(response.body().string());
                    else
                        h.backResponse("error");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                h.backResponse("error");
                S.E("sdcard-err2:" + t.toString());
                S.E("Something went wrong.!");
            }
        });
    }
}