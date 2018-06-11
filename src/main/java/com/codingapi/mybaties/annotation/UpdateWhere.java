package com.codingapi.mybaties.annotation;

import java.lang.annotation.*;

/**
 * @author lorne
 * @date 2018/6/11
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdateWhere {


    String value();


}
