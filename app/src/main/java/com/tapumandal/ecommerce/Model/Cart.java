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

//    @SerializedName("defaultDiscountBtn")
//    protected String defaultDiscountBtn = ""; // radioOnProduct/radioSpecialOffer

//    @SerializedName("discountName")
//    protected String discountName = "Special Offer"; // Special Offer(Eid/Puja/NewYear)

//    @SerializedName("discountType")
//    protected String discountType = "TotalPercentage"; // TotalPercentage/OverallAmount

//    @SerializedName("discountTypeCondition")
//    protected List<DiscountTypeCondition> discountTypeCondition;

//    @SerializedName("discountBanner")
//    protected String discountBanner;

//    @SerializedName("paymentDiscountMessage")
//    protected String paymentDiscountMessage = "If there is any payment discount";

//    @SerializedName("paymentDiscountBanner")
//    protected String paymentDiscountBanner;

//    @SerializedName("cardPaymentDiscountName")
//    protected String cardPaymentDiscountName = "Debit/Credit Cart";

//    @SerializedName("cardPaymentType")
//    protected String cardPaymentDiscountType = "TotalPercentage"; // TotalPercentage/OverallAmount

//    @SerializedName("cardPaymentCondition")
//    protected List<DiscountTypeCondition> cardPaymentCondition;

//    @SerializedName("mobilePaymentDiscountName")
//    protected String mobilePaymentDiscountName = "BKash/Rocket/Nagad";

//    @SerializedName("mobilePaymentType")
//    protected String mobilePaymentDiscountType = "TotalPercentage"; // TotalPercentage/OverallAmount

//    @SerializedName("mobilePaymentCondition")
//    protected List<DiscountTypeCondition> mobilePaymentCondition;


    @SerializedName("selectedDiscountName")
    protected String selectedDiscountName = "On Product/Special Discount/Mobile Payment/Card Payment";

    @SerializedName("selectedDiscountType")
    protected String selectedDiscountType = "TotalPercentage"; // TotalPercentage/OverallAmount

    @SerializedName("selectedDiscountDetails")
    protected String selectedDiscountDetails;


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

    public String getSelectedDiscountName() {
        return selectedDiscountName == null ? "" : selectedDiscountName;
    }

    public void setSelectedDiscountName(String selectedDiscountName) {
        this.selectedDiscountName = selectedDiscountName;
    }

    public String getSelectedDiscountType() {
        return selectedDiscountType == null ? "" : selectedDiscountType;
    }

    public void setSelectedDiscountType(String selectedDiscountType) {
        this.selectedDiscountType = selectedDiscountType;
    }

    public String getSelectedDiscountDetails() {
        return selectedDiscountDetails == null ? "" : selectedDiscountDetails;
    }

    public void setSelectedDiscountDetails(String selectedDiscountDetails) {
        this.selectedDiscountDetails = selectedDiscountDetails;
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
}

