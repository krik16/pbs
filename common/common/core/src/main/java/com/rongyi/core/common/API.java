package com.rongyi.core.common;

/**
 * Created by Breggor on 2015/9/14.
 */
public @interface API {


    String name() default "";

    //业务描述
    String desc() default "";
}
