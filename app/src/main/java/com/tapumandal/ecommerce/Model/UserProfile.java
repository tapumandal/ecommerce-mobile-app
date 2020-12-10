package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tapumandal on 10/25/2020.
 * For any query ask online.tapu@gmail.com
 */
public class UserProfile implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("username")
    private String username;
    @SerializedName("gender")
    private String gender;
    @SerializedName("mobileNo")
    private String mobileNo;
    @SerializedName("mobileNoIsValid")
    private boolean mobileNoIsValid = false;
    @SerializedName("isLoggedIn")
    private boolean isLoggedIn = false;
    @SerializedName("isActive")
    private String isActive;
    @SerializedName("picture")
    private String picture;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("userTokenId")
    private String userTokenId; //Firebase Phone Authentication

    @SerializedName("addresses")
    private List<Address> addresses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUserTokenId() {
        return userTokenId == null ? "" : userTokenId;
    }

    public void setUserTokenId(String userTokenId) {
        this.userTokenId = userTokenId;
    }
}
