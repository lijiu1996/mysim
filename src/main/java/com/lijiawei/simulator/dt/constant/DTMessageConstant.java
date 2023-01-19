package com.lijiawei.simulator.dt.constant;

import com.lijiawei.simulator.dt.bean.dto.*;

/**
 * @author Li JiaWei
 * @ClassName: DTMessageConstant
 * @Description:
 * @Date: 2022/12/23 16:16
 * @Version: 1.0
 */
public class DTMessageConstant {

    public static final int  dataLen_heartBeat = 11;

    public static final byte abstractType_heartBeat = 0x0F;

    public static final byte abstractType_stateRequest = 0x12;

    public static final byte abstractType_dataResponse = (byte)0x8F;

    public static final byte dataType_allState = 1;

    public static final byte dataType_stateChange = 2;

    public static final byte dataType_alarm = 3;

    public static final int  portState_link_up = 1;
    public static final int  portState_link_down = 0;
    public static final int  alarmState_alarm = 1;
    public static final int  alarmState_recovered = 0;

    public static Class<? extends AbstractMsgFrame> getMsgType(byte type) {
        if (type == abstractType_heartBeat) {
            return HeartBeatFrame.class;
        }
        else if (type == abstractType_dataResponse) {
            return AbstractMsgDetailFrame.class;
        }
        else if (type == abstractType_stateRequest){
            return HeartBeatFrame.class;
        } else if (type == dataType_allState) {
            return TotalStateFrame.class;
        } else if (type == dataType_stateChange) {
            return TotalStateFrame.class;
        } else if (type == dataType_alarm) {
            return AlarmFrame.class;
        } else
            return null;
    }
}