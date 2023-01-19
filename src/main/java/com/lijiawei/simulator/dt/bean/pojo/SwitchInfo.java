package com.lijiawei.simulator.dt.bean.pojo;

import com.lijiawei.simulator.dt.bean.pojo.PortInfo;
import com.lijiawei.simulator.dt.util.HexUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: SwitchInfo
 * @Description:
 * @Date: 2022/12/23 16:15
 * @Version: 1.0
 */
@Data
public class SwitchInfo {

    private String ip;
    private int portNum;
    private List<PortInfo> portInfoList;

    private int switchId;

    @Override
    public String toString() {
        return "SwitchInfo{" +
                "ip='" + ip + '\'' +
                "iphex='" + HexUtil.prettyByteArray(HexUtil.ipToByteArray(ip)) + "\'" +
                ", portNum=" + portNum +
//                ", portInfoList=" + portInfoList +
                ", switchId=" + switchId +
                '}';
    }
}
