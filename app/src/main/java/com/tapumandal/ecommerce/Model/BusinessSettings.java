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

    @SerializedName("defaultDiscountBtn")
    protected String defaultDiscountBtn = "radioSpecialOffer"; // radioOnProduct/radioSpecialOffer

    @SerializedName("discountName")
    protected String discountName = "Special Offer"; // Special Offer(Eid/Puja/NewYear)

    @SerializedName("discountBanner")
    protected String discountBanner;

    @SerializedName("discountType")
    protected String discountType = "TotalPercentage"; // TotalPercentage/OverallAmount/ProductDiscount

    @SerializedName("discountTypeCondition")
    protected List<DiscountTypeCondition> discountTypeCondition;



    @SerializedName("paymentDiscountMessage")
    protected String paymentDiscountMessage = "If there is any payment discount";

    @SerializedName("paymentDiscountBanner")
    protected String paymentDiscountBanner;

    @SerializedName("cardPaymentDiscountName")
    protected String cardPaymentDiscountName = "Debit/Credit Card";

    @SerializedName("cardPaymentType")
    protected String cardPaymentDiscountType = "TotalPercentage"; // TotalPercentage/OverallAmount

    @SerializedName("cardPaymentCondition")
    protected List<DiscountTypeCondition> cardPaymentCondition;

    @SerializedName("mobilePaymentDiscountName")
    protected String mobilePaymentDiscountName = "BKash/Rocket/Nagad";

    @SerializedName("mobilePaymentType")
    protected String mobilePaymentDiscountType = "TotalPercentage"; // TotalPercentage/OverallAmount

    @SerializedName("mobilePaymentCondition")
    protected List<DiscountTypeCondition> mobilePaymentCondition;


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

    public String getDefaultDiscountBtn() {
        return defaultDiscountBtn == null ? "" : defaultDiscountBtn;
    }

    public void setDefaultDiscountBtn(String defaultDiscountBtn) {
        this.defaultDiscountBtn = defaultDiscountBtn;
    }

    public String getDiscountName() {
        return discountName == null ? "" : discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountType() {
        return discountType == null ? "" : discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public List<DiscountTypeCondition> getDiscountTypeCondition() {
        return discountTypeCondition;
    }

    public void setDiscountTypeCondition(List<DiscountTypeCondition> discountTypeCondition) {
        this.discountTypeCondition = discountTypeCondition;
    }

    public String getDiscountBanner() {
        return discountBanner == null ? "" : discountBanner;
    }

    public void setDiscountBanner(String discountBanner) {
        this.discountBanner = discountBanner;
    }

    public String getPaymentDiscountMessage() {
        return paymentDiscountMessage == null ? "" : paymentDiscountMessage;
    }

    public void setPaymentDiscountMessage(String paymentDiscountMessage) {
        this.paymentDiscountMessage = paymentDiscountMessage;
    }

    public String getPaymentDiscountBanner() {
        return paymentDiscountBanner == null ? "" : paymentDiscountBanner;
    }

    public void setPaymentDiscountBanner(String paymentDiscountBanner) {
        this.paymentDiscountBanner = paymentDiscountBanner;
    }

    public String getCardPaymentDiscountName() {
        return cardPaymentDiscountName == null ? "" : cardPaymentDiscountName;
    }

    public void setCardPaymentDiscountName(String cardPaymentDiscountName) {
        this.cardPaymentDiscountName = cardPaymentDiscountName;
    }

    public String getCardPaymentDiscountType() {
        return cardPaymentDiscountType == null ? "" : cardPaymentDiscountType;
    }

    public void setCardPaymentDiscountType(String cardPaymentDiscountType) {
        this.cardPaymentDiscountType = cardPaymentDiscountType;
    }

    public List<DiscountTypeCondition> getCardPaymentCondition() {
        return cardPaymentCondition;
    }

    public void setCardPaymentCondition(List<DiscountTypeCondition> cardPaymentCondition) {
        this.cardPaymentCondition = cardPaymentCondition;
    }

    public String getMobilePaymentDiscountName() {
        return mobilePaymentDiscountName == null ? "" : mobilePaymentDiscountName;
    }

    public void setMobilePaymentDiscountName(String mobilePaymentDiscountName) {
        this.mobilePaymentDiscountName = mobilePaymentDiscountName;
    }

    public String getMobilePaymentDiscountType() {
        return mobilePaymentDiscountType == null ? "" : mobilePaymentDiscountType;
    }

    public void setMobilePaymentDiscountType(String mobilePaymentDiscountType) {
        this.mobilePaymentDiscountType = mobilePaymentDiscountType;
    }

    public List<DiscountTypeCondition> getMobilePaymentCondition() {
        return mobilePaymentCondition;
    }

    public void setMobilePaymentCondition(List<DiscountTypeCondition> mobilePaymentCondition) {
        this.mobilePaymentCondition = mobilePaymentCondition;
    }
}
