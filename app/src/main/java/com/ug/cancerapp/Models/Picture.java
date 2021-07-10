package com.ug.cancerapp.Models;

public class Picture {

    private Float negative_confidence;
    private Float positive_confidence;
    private String pred_class;
    private String image_file_name;
    private String image_file_name_outline;

    public Picture(Float negative_confidence, Float positive_confidence, String pred_class, String image_file_name,
                   String image_file_name_outline) {
        this.negative_confidence = negative_confidence;
        this.positive_confidence = positive_confidence;
        this.pred_class = pred_class;
        this.image_file_name = image_file_name;
        this.image_file_name_outline = image_file_name_outline;
    }

    public Float getNegative_confidence() {
        return negative_confidence;
    }

    public void setNegative_confidence(Float negative_confidence) {
        this.negative_confidence = negative_confidence;
    }

    public Float getPositive_confidence() {
        return positive_confidence;
    }

    public void setPositive_confidence(Float positive_confidence) {
        this.positive_confidence = positive_confidence;
    }

    public String getPred_class() {
        return pred_class;
    }

    public void setPred_class(String pred_class) {
        this.pred_class = pred_class;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

    public String getImage_file_name_outline() {
        return image_file_name_outline;
    }

    public void setImage_file_name_outline(String image_file_name_outline) {
        this.image_file_name_outline = image_file_name_outline;
    }
}
