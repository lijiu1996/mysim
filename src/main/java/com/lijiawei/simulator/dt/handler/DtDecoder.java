package com.lijiawei.simulator.dt.handler;

import cn.hutool.core.util.ByteUtil;
import com.lijiawei.simulator.dt.bean.dto.AbstractMsgFrame;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import com.lijiawei.simulator.dt.serializer.ByteBufBasedSerializerAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: DtDecoder
 * @Description:
 * @Date: 2023/1/16 14:00
 * @Version: 1.0
 */
@Slf4j
@Component
public class DtDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Value("${aaa.debugmode}")
    private Boolean debugEnable;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 打印收到的报文信息
        if (debugEnable) {
            log.info("收到报文\n" + ByteBufUtil.prettyHexDump(msg));
        }
        byte version = msg.readByte();
        if (version != DtProtocolHandler.PROTOCOL_VERSION) {
            log.error("报文信息版本错误, 丢弃...");
            return;
        }
        int length = msg.readIntLE();
        if (length <= 0) {
            log.error("报文信息长度错误, 丢弃...");
        }
        byte type = msg.readByte();
        Class<? extends AbstractMsgFrame> msgType = DTMessageConstant.getMsgType(type);
        if (msgType == null) {
            log.error("报文信息类型错误, 丢弃...");
            return;
        }

        ByteBufBasedSerializerAdapter serializer = new ByteBufBasedSerializerAdapter(msg);
        AbstractMsgFrame deserialize = serializer.deserialize(msgType, null);
        if (deserialize != null)
            out.add(deserialize);
    }
}
