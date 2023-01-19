package com.lijiawei.simulator.dt.bean.pojo;

import com.lijiawei.simulator.dt.util.HexUtil;
import lombok.Data;

/**
 * @author Li JiaWei
 * @ClassName: AlarmInfo
 * @Description:
 * @Date: 2022/12/23 15:57
 * @Version: 1.0
 */
@Data
public class AlarmInfo {

    private String ip;
    private byte alarmType;
    private boolean isPortAlarm;
    private byte slotId;
    private byte portId;
    private short alarmState;

    private int id;

    @Override
    public String toString() {
        return "AlarmInfo{" +
                "ip='" + ip + '\'' +
                ",iphex='" + HexUtil.prettyByteArray(HexUtil.ipToByteArray(ip)) + "\'" +
                ", alarmType=" + alarmType +
                ", isPortAlarm=" + isPortAlarm +
                ", slotId=" + slotId +
                ", portId=" + portId +
                ", alarmState=" + alarmState +
                '}';
    }
}
