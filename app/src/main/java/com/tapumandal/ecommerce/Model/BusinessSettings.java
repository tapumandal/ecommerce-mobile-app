package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TapuMandal on 11/4/2020.
 * For any query ask online.tapu@gmail.com
 */
public class BusinessSettings implements Serializable {

    @SerializedName("id")
    protected int id;


    @SerializedName("updateMenu")
    protected boolean updateMenu = true;

    @SerializedName("deliveryCharge")
    protected int deliveryCharge = 20;

    @SerializedName("discountName")
    protected String discountName = "Wednesday Special"; // Special Discount /Product Discount

    @SerializedName("discountType")
    protected String discountType = "TotalPercentage"; // TotalPercentage/OverallAmount/ProductDiscount

    @SerializedName("discountAmount")
    protected int discountAmount = 15;

    @SerializedName("cardPaymentDiscount")
    protected int cardPaymentDiscount = 20;

    @SerializedName("mobilePaymentDiscount")
    protected int mobilePaymentDiscount = 20;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUpdateMenu() {
        return updateMenu;
    }

    public void setUpdateMenu(boolean updateMenu) {
        this.updateMenu = updateMenu;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
