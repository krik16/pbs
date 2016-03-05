package com.rongyi.core.enumerate.malllife;

/**
 * @author jiejie 2014年6月20日 下午1:52:46
 */
public enum AppReferenceEnum {
    CITY("city"), MALL("mall"), SHOP("shop"), BRAND("brand");

    private String value;

    private AppReferenceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isMall(String value) {
        return MALL.getValue().equals(value);
    }

    public static boolean isShop(String value) {
        return SHOP.getValue().equals(value);
    }

    public static boolean isBrand(String value) {
        return BRAND.getValue().equals(value);
    }

    public static boolean isCity(String value) {
        return CITY.getValue().equals(value);
    }
}
