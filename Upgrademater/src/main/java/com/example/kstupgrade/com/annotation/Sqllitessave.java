package com.example.kstupgrade.com.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sqllitessave {
    String value();
    boolean isAop() default true;
}
