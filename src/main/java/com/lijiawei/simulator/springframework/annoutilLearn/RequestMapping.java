package com.lijiawei.simulator.springframework.annoutilLearn;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Li JiaWei
 * @ClassName: RequestMapping
 * @Description:
 * @Date: 2023/1/6 10:57
 * @Version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String name() default "";

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};
}
