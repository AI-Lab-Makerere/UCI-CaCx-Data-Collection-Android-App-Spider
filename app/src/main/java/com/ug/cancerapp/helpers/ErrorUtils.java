/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.helpers;

import com.ug.cancerapp.Activities.SendingActivity;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Models.ApiError;
import com.ug.cancerapp.Worker.LocationUploadWorker;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                ApiClient.getClient().responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError apiError;
        try {
            apiError = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }
        return apiError;
    }
}
