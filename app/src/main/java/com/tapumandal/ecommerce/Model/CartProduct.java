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

    @SerializedName("product_id")
    private int productId;

    @SerializedName("order_quantity")
    private int orderQuantity = 0;

    public CartProduct(Product item) {
        this.productId = item.getId();
        this.orderQuantity = item.getOrderQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
