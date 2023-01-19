package com.lijiawei.simulator.dt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.MergedAnnotation;

@SpringBootApplication
@MapperScan(basePackages = "com.lijiawei.simulator.dt.mapper")
public class DtApplication {

    public static void main(String[] args) {
        SpringBootApplication mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(DtApplication.class, SpringBootApplication.class);
        System.out.println(mergedAnnotation);
//        SpringApplication.run(DtApplication.class, args);
    }

}
