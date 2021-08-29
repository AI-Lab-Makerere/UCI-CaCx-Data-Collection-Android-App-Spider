/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Apis;

import com.ug.cancerapp.Models.Capture2;
import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.CurrentUser;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.Models.Feedback;
import com.ug.cancerapp.Models.Information;
import com.ug.cancerapp.Models.Model;
import com.ug.cancerapp.Models.Nurse;
import com.ug.cancerapp.Models.Review;
import com.ug.cancerapp.Models.Settings;
import com.ug.cancerapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonPlaceHolder {

    @POST("api/login")
    Call<String> login(@Body User user);

    @GET("api/current-user-profile")
    Call<CurrentUser> userProfile(@Header("Authorization") String header);

    @Headers("Accept: application/json")
    @POST("api/capture-entry")
    Call<String> capture(@Header("Authorization") String header, @Body Capture capture);

    @Headers("Accept: application/json")
    @POST("api/capture-entry")
    Call<String> capture2(@Header("Authorization") String header, @Body Capture2 capture2);

    @Headers("Accept: application/json")
    @GET("api/pending-gynecologist-review")
    Call<List<Case>> cases(@Header("Authorization") String header);

    @Headers("Accept: application/json")
    @GET("api/my-entries")
    Call<List<Case>> uploaded(@Header("Authorization") String header);

    @Headers("Accept: application/json")
    @GET("api/my-entries")
    Call<List<Case>> search(@Header("Authorization") String header, @Query("search") String parameter);

    @Headers("Accept: application/json")
    @POST("api/capture-gynecologist-review")
    Call<Review> feedback(@Header("Authorization") String header, @Body Feedback feedback);

    @Headers("Accept: application/json")
    @GET("api/entry/{instanceID}")
    Call<Information> patient(@Header("Authorization") String header, @Path("instanceID") String instanceID);

    @Headers("Accept: application/json")
    @GET("api/my-reviewed-entries")
    Call<List<Case>> reviewed(@Header("Authorization") String header);

    @Headers("Accept: application/json")
    @GET("api/my-reviewed-entries")
    Call<List<Case>> searching(@Header("Authorization") String header, @Query("search") String parameter);

    @Headers("Accept: application/json")
    @GET("api/settings")
    Call<Settings> setting(@Header("Authorization") String header);

    @Headers("Accept: application/json")
    @GET("api/my-gynecologist-reviews")
    Call<List<Model>> model(@Header("Authorization") String header);

    @Headers("Accept: application/json")
    @GET("api/nurses")
    Call<List<Nurse>> nurses(@Header("Authorization") String header);
}
