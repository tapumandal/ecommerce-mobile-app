package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class CartProduct implements Serializable {

    @SerializedName("id")
    protected int id;

    @SerializedName("order_quantity")
    private int orderQuantity = 0;

    public CartProduct(Product item) {
        this.id = item.getId();
        this.orderQuantity = item.getOrderQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
