package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tapumandal on 10/25/2020.
 * For any query ask online.tapu@gmail.com
 */
public class LoginResponse implements Serializable {
    @SerializedName("jwt")
    private String jwt;

    @SerializedName("user")
    private UserProfile user;

    public String getJwt() {
        return jwt == null ? "" : jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
