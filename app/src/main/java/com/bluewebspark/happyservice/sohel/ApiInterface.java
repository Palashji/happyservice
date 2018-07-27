package com.bluewebspark.happyservice.sohel;

/**
 * Created by abc on 09-Mar-18.
 */

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("categories")
    Call<ResponseBody> getCategories();

    @GET("subcategories")
    Call<ResponseBody> getSubCategories(
            @Query("id") String catId
    );

    @GET("services")
    Call<ResponseBody> getServices(
            @Query("catID") String catId
    );

    @GET("services")
    Call<ResponseBody> getServiceDetails(
            @Query("id") String id
    );

    @GET("my_requested_service")
    Call<ResponseBody> getOnGoing(
            @Query("userID") String userID
    );

    @GET("requeted_service_details")
    Call<ResponseBody> requeted_service_details(
            @Query("requestID") String requestID
    );

    @GET("my_completed_service")
    Call<ResponseBody> my_completed_service(
            @Query("userID") String userID
    );

    @FormUrlEncoded
    @POST("user_registration")
    Call<ResponseBody> userRegistration(
            @Field("userName") String userName,
            @Field("userEmail") String userEmail,
            @Field("userPhone") String userPhone,
            @Field("userPassword") String userPassword,
            @Field("userType") String userType,
            @Field("userAddress") String userAddress,
            @Field("userLat") String userLat,
            @Field("userLong") String userLong,
            @Field("userBySocial") String userBySocial,
            @Field("userfirbaseID") String userfirbaseID
    );

    @FormUrlEncoded
    @POST("user_login")
    Call<ResponseBody> userLogin(
            @Field("userPhone") String userPhone,
            @Field("userPassword") String userPassword,
            @Field("userType") String userType
    );

    @FormUrlEncoded
    @POST("update_userProfile")
    Call<ResponseBody> update_userProfile(
            @Field("userID") String userID,
            @Field("userImage") String userImage,
            @Field("userName") String userName,
            @Field("userAlternatePhone") String userAlternatePhone,
            @Field("userfirbaseID") String userfirbaseID
    );

    @FormUrlEncoded
    @POST("request_service")
    Call<ResponseBody> serviceBooking(
            @Field("serviceID") String serviceID,
            @Field("userID") String userID,
            @Field("servicePrice") String servicePrice,
            @Field("serviceReqAddress") String serviceReqAddress,
            @Field("serviceReqNearby") String serviceReqNearby,
            @Field("serviceReqCity") String serviceReqCity,
            @Field("serviceReqDate") String serviceReqDate,
            @Field("serviceReqTime") String serviceReqTime,
            @Field("requestLat") String requestLat,
            @Field("requestLong") String requestLong,
            @Field("requestStatus") String requestStatus
    );

    @FormUrlEncoded
    @POST("change_request_status")
    Call<ResponseBody> change_request_status(
            @Field("requestID") String requestID,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("chat_initiate")
    Call<ResponseBody> chat_initiate(
            @Field("userID") String userID,
            @Field("vendorID") String vendorID,
            @Field("serviceRequestID") String serviceRequestID,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("review")
    Call<ResponseBody> submitReview(
            @Field("serviceRequestID") String serviceRequestID,
            @Field("reviewBy") String reviewBy,
            @Field("reviewFor") String reviewFor,
            @Field("reviewComment") String reviewComment,
            @Field("ratingNumber") float ratingNumber
    );

    @FormUrlEncoded
    @POST("chatting_history")
    Call<ResponseBody> chatting_history(
            @Field("messageBY") String messageBY,
            @Field("messageTo") String messageTo,
            @Field("serviceRequestID") String serviceRequestID
    );

    @FormUrlEncoded
    @POST("chatting")
    Call<ResponseBody> sendChatMsg(
            @Field("serviceRequestID") String serviceRequestID,
            @Field("messageBY") String messageBY,
            @Field("messageTo") String messageTo,
            @Field("chat") String chat
    );

    @FormUrlEncoded
    @POST("hiring_vendor")
    Call<ResponseBody> hiring_vendor(
            @Field("vendorID") String vendorID,
            @Field("requestID") String requestID
    );

    @GET("user_details")
    Call<ResponseBody> getUseretails(
            @Query("userID") String user_id
    );

    @GET("chat_list")
    Call<ResponseBody> chat_list(
            @Query("userID") String userID
    );

    @GET("service_location")
    Call<ResponseBody> service_location(
            @Query("cityName") String cityName
    );

    @GET("app_home_baseCate")
    Call<ResponseBody> app_home_baseCate();

    @FormUrlEncoded
    @POST("fcm_update")
    Call<ResponseBody> updateFcmID(
            @Query("user_id") String user_id,
            @Query("fcm_id") String fcm_id
    );
}