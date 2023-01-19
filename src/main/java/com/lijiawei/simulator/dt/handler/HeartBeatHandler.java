package com.lijiawei.simulator.dt.handler;

import com.lijiawei.simulator.dt.bean.dto.HeartBeatFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Li JiaWei
 * @ClassName: HeartBeatHandler
 * @Description:
 * @Date: 2023/1/18 15:48
 * @Version: 1.0
 */
@Slf4j
public class HeartBeatHandler extends SimpleChannelInboundHandler<HeartBeatFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatFrame msg) throws Exception {
        log.info("收到心跳报文{}",msg);
    }
}
