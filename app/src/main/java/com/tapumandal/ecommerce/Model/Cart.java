package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TapuMandal on 11/4/2020.
 * For any query ask online.tapu@gmail.com
 */
public class Cart implements Serializable {

    @SerializedName("id")
    protected int id;

    @SerializedName("deliveryCharge")
    protected int deliveryCharge = 0;

    @SerializedName("defaultDiscountBtn")
    protected String defaultDiscountBtn = ""; // radioOnProduct/radioSpecialOffer

    @SerializedName("discountName")
    protected String discountName = "Special Offer"; // Special Offer(Eid/Puja/NewYear)

    @SerializedName("discountType")
    protected String discountType = "TotalPercentage"; // TotalPercentage/OverallAmount

    @SerializedName("discountTypeCondition")
    protected List<DiscountTypeCondition> discountTypeCondition;

    @SerializedName("discountBanner")
    protected String discountBanner;

    @SerializedName("paymentDiscountMessage")
    protected String paymentDiscountMessage = "If there is any payment discount";

    @SerializedName("paymentDiscountBanner")
    protected String paymentDiscountBanner;

    @SerializedName("cardPaymentDiscountName")
    protected String cardPaymentDiscountName = "Debit/Credit Cart";

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



    @SerializedName("totalProductDiscount")
    protected int totalProductDiscount = 0;

    @SerializedName("totalProductQuantity")
    protected int totalProductQuantity = 0;

    @SerializedName("totalProductPrice")
    protected int totalProductPrice = 0 ;

    @SerializedName("totalDiscount")
    protected int totalDiscount = 0;

    @SerializedName("totalPayable")
    protected int totalPayable = 0;

    @SerializedName("productList")
    protected List<Product> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDefaultDiscountBtn() {
        return defaultDiscountBtn;
    }

    public void setDefaultDiscountBtn(String defaultDiscountBtn) {
        this.defaultDiscountBtn = defaultDiscountBtn;
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

    public List<DiscountTypeCondition> getDiscountTypeCondition() {
        return discountTypeCondition;
    }

    public void setDiscountTypeCondition(List<DiscountTypeCondition> discountTypeCondition) {
        this.discountTypeCondition = discountTypeCondition;
    }

    public String getDiscountBanner() {
        return discountBanner;
    }

    public void setDiscountBanner(String discountBanner) {
        this.discountBanner = discountBanner;
    }

    public int getTotalProductDiscount() {
        return totalProductDiscount;
    }

    public void setTotalProductDiscount(int totalProductDiscount) {
        this.totalProductDiscount = totalProductDiscount;
    }

    public int getTotalProductQuantity() {
        return totalProductQuantity;
    }

    public void setTotalProductQuantity(int totalProductQuantity) {
        this.totalProductQuantity = totalProductQuantity;
    }

    public int getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(int totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(int totalPayable) {
        this.totalPayable = totalPayable;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPaymentDiscountMessage() {
        return paymentDiscountMessage;
    }

    public void setPaymentDiscountMessage(String paymentDiscountMessage) {
        this.paymentDiscountMessage = paymentDiscountMessage;
    }

    public String getPaymentDiscountBanner() {
        return paymentDiscountBanner;
    }

    public void setPaymentDiscountBanner(String paymentDiscountBanner) {
        this.paymentDiscountBanner = paymentDiscountBanner;
    }

    public String getCardPaymentDiscountName() {
        return cardPaymentDiscountName;
    }

    public void setCardPaymentDiscountName(String cardPaymentDiscountName) {
        this.cardPaymentDiscountName = cardPaymentDiscountName;
    }

    public List<DiscountTypeCondition> getCardPaymentCondition() {
        return cardPaymentCondition;
    }

    public void setCardPaymentCondition(List<DiscountTypeCondition> cardPaymentCondition) {
        this.cardPaymentCondition = cardPaymentCondition;
    }

    public String getMobilePaymentDiscountName() {
        return mobilePaymentDiscountName;
    }

    public void setMobilePaymentDiscountName(String mobilePaymentDiscountName) {
        this.mobilePaymentDiscountName = mobilePaymentDiscountName;
    }


    public List<DiscountTypeCondition> getMobilePaymentCondition() {
        return mobilePaymentCondition;
    }

    public void setMobilePaymentCondition(List<DiscountTypeCondition> mobilePaymentCondition) {
        this.mobilePaymentCondition = mobilePaymentCondition;
    }

    public String getCardPaymentDiscountType() {
        return cardPaymentDiscountType;
    }

    public void setCardPaymentDiscountType(String cardPaymentDiscountType) {
        this.cardPaymentDiscountType = cardPaymentDiscountType;
    }

    public String getMobilePaymentDiscountType() {
        return mobilePaymentDiscountType;
    }

    public void setMobilePaymentDiscountType(String mobilePaymentDiscountType) {
        this.mobilePaymentDiscountType = mobilePaymentDiscountType;
    }
}

