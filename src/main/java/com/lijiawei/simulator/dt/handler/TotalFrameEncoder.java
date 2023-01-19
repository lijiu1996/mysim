package com.lijiawei.simulator.dt.handler;

import com.lijiawei.simulator.dt.bean.dto.AbstractMsgDetailFrame;
import com.lijiawei.simulator.dt.bean.dto.TotalStateFrame;
import com.lijiawei.simulator.dt.bean.pojo.SwitchInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: TotalFrameEncoder
 * @Description:
 * @Date: 2023/1/18 16:21
 * @Version: 1.0
 */
@Slf4j
public class TotalFrameEncoder extends MessageToMessageEncoder<TotalStateFrame> {

    public static final int splitSize = 10;

    @Override
    protected void encode(ChannelHandlerContext ctx, TotalStateFrame msg, List<Object> out) throws Exception {
        List<SwitchInfo> switchInfoList = msg.getSwitchInfoList();
        if (switchInfoList.size() <= splitSize) {
            out.add(msg);
        } else {
            int totalFrame = (switchInfoList.size() + splitSize - 1) / splitSize;
            int frameSerial = 1;
            int time = (int)(System.currentTimeMillis()/1000);
            while (frameSerial < totalFrame) {
                TotalStateFrame totalStateFrame = new TotalStateFrame(switchInfoList.subList((frameSerial - 1) * splitSize, frameSerial * splitSize ));
                totalStateFrame.setTotalFrame(totalFrame);
                totalStateFrame.setFrameSerial(frameSerial);
                totalStateFrame.setTime(time);
                out.add(totalStateFrame);
                frameSerial++;
            }
            TotalStateFrame totalStateFrame = new TotalStateFrame(switchInfoList.subList((frameSerial - 1) * splitSize, switchInfoList.size()));
            totalStateFrame.setTotalFrame(totalFrame);
            totalStateFrame.setFrameSerial(frameSerial);
            totalStateFrame.setTime(time);
            out.add(totalStateFrame);
        }
    }
}
