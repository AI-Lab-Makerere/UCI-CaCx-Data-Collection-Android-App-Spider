package com.ug.cancerapp.Apis;

import com.ug.cancerapp.Models.CurrentUser;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolder {

    @POST("api/login")
    Call<String> login(@Body User user);

    @GET("api/current-user-profile")
    Call<CurrentUser> userProfile(@Header("Authorization") String header);

    @POST("api/capture-entry")
    Call<String> capture(@Header("Authorization") String header, @Body Capture capture);
}
