package com.lijiawei.simulator.dt.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Li JiaWei
 * @ClassName: SwitchInfoEntity
 * @Description:
 * @Date: 2023/1/4 11:42
 * @Version: 1.0
 */
@TableName("t_dt_switch_info")
@Data
public class SwitchInfoEntity {

    private String ip;
    @TableId
    private Integer switchId;

}
