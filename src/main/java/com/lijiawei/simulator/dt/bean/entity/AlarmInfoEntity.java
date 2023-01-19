package com.lijiawei.simulator.dt.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Li JiaWei
 * @ClassName: AlarmInfoEntity
 * @Description:
 * @Date: 2023/1/5 15:06
 * @Version: 1.0
 */
@TableName("t_dt_alarm_info")
@Data
public class AlarmInfoEntity {

    private String ip;
    private byte alarmType;
    private byte slotId;
    private byte portId;
    private short alarmState;

}
