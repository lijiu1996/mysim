package com.lijiawei.simulator.springframework.annoutilLearn;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @author Li JiaWei
 * @ClassName: SpringLearn1
 * @Description:    AnnotationUtil 工具使用
 * @Date: 2023/1/6 10:05
 * @Version: 1.0
 */
public class SpringLearn1 {

    public static void main(String[] args) {
        SingletonService service = new SingletonService();

//        jdk 提供的注解工具无法递归解析
//        SingletonComponent annotation = SingletonService.class.getAnnotation(SingletonComponent.class);
//        System.out.println(annotation.value());
//        Component annotation1 = SingletonService.class.getAnnotation(Component.class);
//        System.out.println(annotation1);

        SingletonComponent annotation = AnnotationUtils.getAnnotation(SingletonService.class, SingletonComponent.class);
        System.out.println(annotation.value());
        Component annotation1 = AnnotationUtils.getAnnotation(SingletonService.class, Component.class);
        System.out.println(annotation1);
        System.out.println(annotation1.value());
        System.out.println("-----");
    }
}
