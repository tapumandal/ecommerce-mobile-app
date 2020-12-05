package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TapuMandal on 11/4/2020.
 * For any query ask online.tapu@gmail.com
 */
public class PromoCode implements Serializable {

    @SerializedName("id")
    protected int id;

    @SerializedName("promoDiscount")
    protected int promoDiscount = 0;

    @SerializedName("promoMessage")
    protected String promoMessage = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(int promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public String getPromoMessage() {
        return promoMessage;
    }

    public void setPromoMessage(String promoMessage) {
        this.promoMessage = promoMessage;
    }
}

