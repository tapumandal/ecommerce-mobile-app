package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DiscountTypeCondition implements Serializable {

    @SerializedName("minimumPurchaseLimit")
    private int minimumPurchaseLimit = 0;

    @SerializedName("discountedAmount")
    private int discountedAmount = 0;

    @SerializedName("maximumDiscountedAmount")
    private int maximumDiscountedAmount = 0;




    public int getMinimumPurchaseLimit() {
        return minimumPurchaseLimit;
    }

    public void setMinimumPurchaseLimit(int minimumPurchaseLimit) {
        this.minimumPurchaseLimit = minimumPurchaseLimit;
    }

    public int getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(int discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public int getMaximumDiscountedAmount() {
        return maximumDiscountedAmount;
    }

    public void setMaximumDiscountedAmount(int maximumDiscountedAmount) {
        this.maximumDiscountedAmount = maximumDiscountedAmount;
    }
}
