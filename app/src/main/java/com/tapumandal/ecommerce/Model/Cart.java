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

    @SerializedName("discountType")
    protected String discountType; // TotalPercentage/OverallAmount/ProductDiscount

    @SerializedName("totalDiscount")
    protected int totalDiscount = 0;



    @SerializedName("totalProductQuantity")
    protected int totalProductQuantity = 0;

    @SerializedName("totalProductPrice")
    protected int totalProductPrice = 0 ;

    @SerializedName("totalProductDiscountedPrice")
    protected int totalProductDiscountedPrice = 0;



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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
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

    public int getTotalProductDiscountedPrice() {
        return totalProductDiscountedPrice;
    }

    public void setTotalProductDiscountedPrice(int totalProductDiscountedPrice) {
        this.totalProductDiscountedPrice = totalProductDiscountedPrice;
    }
}
