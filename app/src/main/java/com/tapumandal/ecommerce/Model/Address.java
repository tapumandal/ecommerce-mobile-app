package com.tapumandal.ecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tapumandal on 10/25/2020.
 * For any query ask online.tapu@gmail.com
 */
public class Address implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("mobileNo")
    private String mobileNo;

    @SerializedName("area")
    private String area;

    @SerializedName("block")
    private String block;

    @SerializedName("road")
    private String road;

    @SerializedName("house")
    private String house;

    @SerializedName("flat")
    private String flat;

    @SerializedName("details")
    private String details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", area='" + area + '\'' +
                ", block='" + block + '\'' +
                ", road='" + road + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
