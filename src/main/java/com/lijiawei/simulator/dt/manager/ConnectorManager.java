package com.lijiawei.simulator.dt.manager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Li JiaWei
 * @ClassName: ConnectorManager
 * @Description:
 * @Date: 2022/12/23 16:55
 * @Version: 1.0
 */
public class ConnectorManager {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup workers = new NioEventLoopGroup();

        ServerBootstrap boot = new ServerBootstrap();
        boot.group(boss,workers)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInboundHandlerAdapter());
    }
}
