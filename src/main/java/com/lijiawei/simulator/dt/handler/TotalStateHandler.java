package com.lijiawei.simulator.dt.handler;

import com.lijiawei.simulator.dt.bean.dto.TotalStateFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Li JiaWei
 * @ClassName: TotalStateHandler
 * @Description:
 * @Date: 2023/1/18 15:49
 * @Version: 1.0
 */
@Slf4j
public class TotalStateHandler extends SimpleChannelInboundHandler<TotalStateFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TotalStateFrame msg) throws Exception {
        log.info("收到全量state报文:{}",msg);
    }
}
