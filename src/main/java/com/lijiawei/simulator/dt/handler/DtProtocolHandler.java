package com.lijiawei.simulator.dt.handler;


import com.lijiawei.simulator.dt.bean.dto.AbstractMsgFrame;
import com.lijiawei.simulator.dt.bean.dto.HeartBeatFrame;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import com.lijiawei.simulator.dt.serializer.ByteBufBasedSerializerAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: DtProtocol
 * @Description: 根据协议进行对象与字节数组之间的转换
 * @Date: 2023/1/3 10:07
 * @Version: 1.0
 */
@Slf4j
public class DtProtocolHandler extends MessageToMessageCodec<ByteBuf, AbstractMsgFrame> {

    public static final byte PROTOCOL_VERSION = 0x10;

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMsgFrame msg, List<Object> out) throws Exception {
        ByteBufBasedSerializerAdapter serializer = new ByteBufBasedSerializerAdapter(ctx.alloc().buffer());
        byte[] bytes = serializer.serialize(msg);
        if (bytes != null) {
            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeByte(PROTOCOL_VERSION);
            buffer.writeIntLE(bytes.length);
            buffer.writeByte(msg.getFrameType());
            buffer.writeBytes(bytes);
            out.add(buffer);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte version = msg.readByte();
        if (version != PROTOCOL_VERSION) {
            log.error("收到错误的版本号信息...,version:{}",version);
            return;
        }
        int length = msg.readInt();
        byte frameType = msg.readByte();
        byte[] frameContent = new byte[length];
        msg.readBytes(frameContent);
        if (DTMessageConstant.abstractType_heartBeat == frameType || DTMessageConstant.abstractType_stateRequest == frameType) {
            HeartBeatFrame heartBeatFrame = new HeartBeatFrame();
            heartBeatFrame.setFrameType(frameType);
            int stamp = msg.readInt();
            heartBeatFrame.setTimestamp(stamp);
            out.add(heartBeatFrame);
        } else if (DTMessageConstant.abstractType_dataResponse == frameType) {
            byte msgType = frameContent[0];
            ByteBufBasedSerializerAdapter serializer = new ByteBufBasedSerializerAdapter(ctx.alloc().buffer());
            Object o = serializer.deserialize(null, frameContent);

        }
    }
}
