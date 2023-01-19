package com.lijiawei.simulator.dt.serializer;

import com.lijiawei.simulator.dt.bean.dto.*;
import com.lijiawei.simulator.dt.bean.pojo.AlarmInfo;
import com.lijiawei.simulator.dt.bean.pojo.SwitchInfo;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: MySerializer
 * @Description: 自定义的基于ByteBuf的序列化器(适配器)
 * @Date: 2023/1/3 10:40
 * @Version: 1.0
 */
@Slf4j
public class ByteBufBasedSerializerAdapter implements MySerializer{

    public ByteBufBasedSerializerAdapter(ByteBuf buffer) {
        this.buffer = buffer;
    }

    private ByteBuf buffer;

    @Override
    public byte[] serialize(Object object) {
        return ByteBufBasedSerializer.Default.serialize(buffer,object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return ByteBufBasedSerializer.Default.deserialize(buffer,clazz);
    }

}
