package com.tapumandal.ecommerce.Model;

import java.io.Serializable;

public class DiscountTypeCondition implements Serializable {
    private int minimumAmount = 0;
    private int discountedAmount = 0;

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public int getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(int discountedAmount) {
        this.discountedAmount = discountedAmount;
    }
}
