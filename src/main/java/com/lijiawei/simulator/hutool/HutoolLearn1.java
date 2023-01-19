package com.lijiawei.simulator.hutool;

import cn.hutool.core.convert.Convert;

import java.time.LocalDateTime;

/**
 * @author Li JiaWei
 * @ClassName: HutoolTest
 * @Description:
 * @Date: 2023/1/6 15:12
 * @Version: 1.0
 */
public class HutoolLearn1 {

    public static void main(String[] args) {
        convert();
    }

    private static void convert() {

        LocalDateTime localDateTime = Convert.toLocalDateTime("2017-05-06");
        System.out.println(localDateTime);
    }
}


