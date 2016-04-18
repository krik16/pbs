package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class Commodity extends BaseEntity implements Serializable {
    private String name;

    private String desc;

    private Integer categoryId;

    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "name='" + name + '\'' +
                "price='" + price + '\'' +
                ", desc='" + desc + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}