package com.lijiawei.simulator.dt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Li JiaWei
 * @ClassName: SimulatorConfig
 * @Description:
 * @Date: 2022/12/23 16:15
 * @Version: 1.0
 */
@PropertySource(value = "classpath:simulator.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "sim")
@Configuration
@Data
public class SimulatorConfig {

    private Integer port; //理论上应该根据前台用户输入启动接收程序
}
