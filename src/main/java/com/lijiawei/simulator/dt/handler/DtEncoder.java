package com.lijiawei.simulator.dt.handler;

import com.lijiawei.simulator.dt.bean.dto.AbstractMsgFrame;
import com.lijiawei.simulator.dt.serializer.ByteBufBasedSerializerAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Li JiaWei
 * @ClassName: DtEncoder
 * @Description:
 * @Date: 2023/1/4 10:40
 * @Version: 1.0
 */
public class DtEncoder extends MessageToByteEncoder<AbstractMsgFrame> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMsgFrame msg, ByteBuf out) throws Exception {
        ByteBufBasedSerializerAdapter serializer = new ByteBufBasedSerializerAdapter(ctx.alloc().buffer());
        byte[] bytes = serializer.serialize(msg);
        if (bytes != null) {
            out.writeByte(0x10);
            out.writeIntLE(bytes.length);
            out.writeByte(msg.getFrameType());
            out.writeBytes(bytes);
        }
    }
}
