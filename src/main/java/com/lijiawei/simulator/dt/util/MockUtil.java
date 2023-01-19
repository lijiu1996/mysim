package com.lijiawei.simulator.dt.util;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Li JiaWei
 * @ClassName: MockUtil
 * @Description: 模拟数据构造
 * @Date: 2023/1/5 11:10
 * @Version: 1.0
 */
public class MockUtil {

    public static String getMockIp() {
        return IntStream.generate(() -> RandomUtil.randomInt(255) + 1).limit(4)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining("."));
    }

    public static void main(String[] args) {
        System.out.println(getMockIp());
    }
}
