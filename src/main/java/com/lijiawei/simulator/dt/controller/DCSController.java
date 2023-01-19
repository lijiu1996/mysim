package com.lijiawei.simulator.dt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Li JiaWei
 * @ClassName: DCSController
 * @Description:
 * @Date: 2022/12/23 16:16
 * @Version: 1.0
 */
@RequestMapping("/dcs")
@Slf4j
@RestController
public class DCSController {

    @RequestMapping("hello")
    private String hello() {
        return "hello";
    }
}