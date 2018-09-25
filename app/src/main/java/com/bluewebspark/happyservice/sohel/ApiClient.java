package com.bluewebspark.happyservice.sohel;

/**
 * Created by abc on 09-Mar-18.
 */

import retrofit2.Retrofit;

public class ApiClient {

    private static final String BASE_URL = "http://happyservice.in/index.php/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}