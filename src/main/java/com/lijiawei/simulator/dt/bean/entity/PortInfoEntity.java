package com.lijiawei.simulator.dt.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Li JiaWei
 * @ClassName: PortInfoEntity
 * @Description:
 * @Date: 2023/1/4 11:42
 * @Version: 1.0
 */
@TableName("t_dt_port_info")
@Data
public class PortInfoEntity {

    private Integer portId;
    private Integer switchId;
    private Integer slotId;
    private Integer state;
}
