package com.example.springboot.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 确保注解在运行时是可见的
@Documented
public @interface AuthAccess {
}
