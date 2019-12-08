package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解
@Retention(RetentionPolicy.RUNTIME) //运行时开始
@Target(ElementType.METHOD) //运用在方法上
public @interface GoeasyAnnotation {
}
