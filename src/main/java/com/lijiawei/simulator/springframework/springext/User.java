package com.lijiawei.simulator.springframework.springext;

import lombok.Data;

import java.util.Date;

/**
 * @author Li JiaWei
 * @ClassName: User
 * @Description:
 * @Date: 2023/1/12 16:19
 * @Version: 1.0
 */
@Data
public class User {

    private Long id;
    private String name;
    private Date registerDate;
}
