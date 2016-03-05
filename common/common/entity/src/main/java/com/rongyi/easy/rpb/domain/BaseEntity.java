package com.rongyi.easy.rpb.domain;

public class BaseEntity {

    public Class<?> getEntityMapperClass() {
        throw new RuntimeException(this.getClass().getName() + "not implement getEntityMapperClass method!");
    }
    
}
