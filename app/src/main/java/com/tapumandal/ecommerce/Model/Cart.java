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
    protected int deliveryCharge;

    @SerializedName("discountType")
    protected String discountType; // TotalPercentage/OverallAmount/ProductDiscount

    @SerializedName("totalDiscount")
    protected int totalDiscount;

    @SerializedName("totalProductPrice")
    protected int totalProductPrice;

    @SerializedName("totalPayable")
    protected int totalPayable;

    @SerializedName("productList")
    protected List<CartProduct> cartProducts;


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

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
