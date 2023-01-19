package com.lijiawei.simulator.dt.handler;

import com.lijiawei.simulator.dt.bean.dto.AlarmFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Li JiaWei
 * @ClassName: AlarmHandler
 * @Description:
 * @Date: 2023/1/18 15:49
 * @Version: 1.0
 */
@Slf4j
public class AlarmHandler extends SimpleChannelInboundHandler<AlarmFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AlarmFrame msg) throws Exception {
        log.info("收到报警报文{}",msg);
    }
}
