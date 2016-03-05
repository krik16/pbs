package com.rongyi.core.enumerate.malllife;

/**
 * 类ContentTypeEnum.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月19日 下午3:25:12
 */
public enum ContentTypeEnum {
    ACTIVITY("activity"), ARTICLE("article"), SAMECITY("sameCity");

    private String value;

    private ContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isActivity(String value) {
        return ACTIVITY.getValue().equals(value);
    }

}
