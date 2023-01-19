package com.lijiawei.simulator.dt.bean.dto;

import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import lombok.Data;

/**
 * @author Li JiaWei
 * @ClassName: HeartBeatFrame
 * @Description:
 * @Date: 2023/1/3 11:30
 * @Version: 1.0
 */
@Data
public class HeartBeatFrame extends AbstractMsgFrame {

    private int timestamp;

    public HeartBeatFrame() {
        this.setFrameType(DTMessageConstant.abstractType_heartBeat);
        this.timestamp = (int) (System.currentTimeMillis()/1000);
    }
}
