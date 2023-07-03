package com.dyc.annotiation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//表示此注解应该在运行时仍然可用，因此可以通过反射检索注解的信息。
@Retention(RetentionPolicy.RUNTIME)

//表示此注解可以应用于方法上
@Target({ElementType.METHOD})
//自定义注解
public @interface SystemLog {

    String basinessName();
}
