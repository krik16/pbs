package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class Authority implements Serializable {
    private static final long serialVersionUID = -8390317344511649368L;
    private Integer id;

    private String name;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}