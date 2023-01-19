package com.lijiawei.simulator.dt.bean.dto;

import com.lijiawei.simulator.dt.bean.pojo.AlarmInfo;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: AlarmFrame
 * @Description:
 * @Date: 2022/12/23 15:57
 * @Version: 1.0
 */
@NoArgsConstructor
@Data
public class AlarmFrame extends AbstractMsgDetailFrame {

    private List<AlarmInfo> alarmList;

    public AlarmFrame(List<AlarmInfo> alarmList) {
        super();
        this.setMsgType(DTMessageConstant.dataType_alarm);
        this.alarmList = alarmList;
    }
}

