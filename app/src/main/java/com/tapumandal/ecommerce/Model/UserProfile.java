package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tapumandal on 10/25/2020.
 * For any query ask online.tapu@gmail.com
 */
public class UserProfile {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("mobileNoIsValid")
    @Expose
    private boolean mobileNoIsValid = false;
    @SerializedName("isActive")
    @Expose
    private String isActive;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("address")
    @Expose
    private List<Address> address;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt == null ? "" : createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt == null ? "" : updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo == null ? "" : mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isMobileNoIsValid() {
        return mobileNoIsValid;
    }

    public void setMobileNoIsValid(boolean mobileNoIsValid) {
        this.mobileNoIsValid = mobileNoIsValid;
    }

    public String getIsActive() {
        return isActive == null ? "" : isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPicture() {
        return picture == null ? "" : picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAccessToken() {
        return accessToken == null ? "" : accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
