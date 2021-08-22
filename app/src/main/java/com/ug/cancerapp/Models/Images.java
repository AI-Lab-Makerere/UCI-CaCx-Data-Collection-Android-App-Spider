/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("picture1_before")
    private Picture1_before picture1_before;
    @SerializedName("picture2_before")
    private Picture2_before picture2_before;
    @SerializedName("picture3_after")
    private Picture3_before picture3_before;
    @SerializedName("picture4_after")
    private Picture4_before picture4_before;

    public Images(Picture1_before picture1_before, Picture2_before picture2_before,
                  Picture3_before picture3_before, Picture4_before picture4_before) {
        this.picture1_before = picture1_before;
        this.picture2_before = picture2_before;
        this.picture3_before = picture3_before;
        this.picture4_before = picture4_before;
    }

    public Picture1_before getPicture1_before() {
        return picture1_before;
    }

    public Picture2_before getPicture2_before() {
        return picture2_before;
    }

    public Picture3_before getPicture3_before() {
        return picture3_before;
    }

    public Picture4_before getPicture4_before() {
        return picture4_before;
    }
}
