package com.lijiawei.simulator.dt.bean.dto;

import com.lijiawei.simulator.dt.bean.pojo.SwitchInfo;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: TotalStateFrame
 * @Description:
 * @Date: 2022/12/23 16:15
 * @Version: 1.0
 */
@NoArgsConstructor
@Data
public class TotalStateFrame extends AbstractMsgDetailFrame {

    private List<SwitchInfo> switchInfoList;

    public TotalStateFrame(List<SwitchInfo> switchInfoList) {
        super();
        this.setMsgType(DTMessageConstant.dataType_allState);
        this.switchInfoList = switchInfoList;
    }
}

