package com.sucl.sbjms.core.annotation;

import java.lang.annotation.*;

/**
 * @author sucl
 * @date 2019/4/3
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface QueryCondition {

    Class<?> domain();
}
