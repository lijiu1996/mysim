package com.lijiawei.simulator.dt.bean.dto;

import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import lombok.Data;

/**
 * @author Li JiaWei
 * @ClassName: AbstractMsgDetailFrame
 * @Description:
 * @Date: 2022/12/23 15:56
 * @Version: 1.0
 */
@Data
public class AbstractMsgDetailFrame extends AbstractMsgFrame {

    private byte msgType;
    private int totalFrame;
    private int frameSerial;
    private int time;

    public AbstractMsgDetailFrame() {
        this.setFrameType(DTMessageConstant.abstractType_dataResponse);
        this.setTime((int) (System.currentTimeMillis()/1000));
    }
}
