package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tapumandal on 10/24/2020.
 * For any query ask online.tapu@gmail.com
 */
public class CommonResponseArray<Item> {


    @SerializedName("errors")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Item> data = null;
    @SerializedName("ErrorMgs")
    @Expose
    private String errorMgs;
    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("action")
    @Expose
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Item> getData() {
        return data;
    }

    public void setData(ArrayList<Item> data) {
        this.data = data;
    }

    public String getErrorMgs() {
        return errorMgs;
    }

    public void setErrorMgs(String errorMgs) {
        this.errorMgs = errorMgs;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
