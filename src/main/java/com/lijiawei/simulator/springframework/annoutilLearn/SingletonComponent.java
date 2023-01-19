package com.lijiawei.simulator.springframework.annoutilLearn;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Li JiaWei
 * @ClassName: SingletonComponent
 * @Description:
 * @Date: 2023/1/6 10:06
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("singleton")
@Inherited
public @interface SingletonComponent {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";
}
