package com.lijiawei.simulator.dt.serializer;

import com.lijiawei.simulator.dt.bean.dto.*;
import com.lijiawei.simulator.dt.bean.entity.PortInfoEntity;
import com.lijiawei.simulator.dt.bean.pojo.AlarmInfo;
import com.lijiawei.simulator.dt.bean.pojo.PortInfo;
import com.lijiawei.simulator.dt.bean.pojo.SwitchInfo;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import com.lijiawei.simulator.dt.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Li JiaWei
 * @ClassName: ByteBufBasedSerializer
 * @Description:
 * @Date: 2023/1/16 15:03
 * @Version: 1.0
 */
@Slf4j
public class ByteBufBasedSerializer {

    private ByteBufBasedSerializer() {}

    public static ByteBufBasedSerializer Default = new ByteBufBasedSerializer();

    public static final byte[] fixByte_7 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};

    public static final byte[] fixByte_end = {(byte)0x43,(byte)0x52,(byte)0x53,(byte)0x43};

    public byte[] serialize(ByteBuf buffer, Object object) {
        AbstractMsgFrame frame = (AbstractMsgFrame)object;
        byte frameType = frame.getFrameType();
        if (frameType == DTMessageConstant.abstractType_heartBeat || frameType == DTMessageConstant.abstractType_stateRequest) {
            HeartBeatFrame heartBeatFrame = (HeartBeatFrame) object;
            buffer.writeIntLE(heartBeatFrame.getTimestamp());
            buffer.writeBytes(fixByte_7);
        } else if(frameType == DTMessageConstant.abstractType_dataResponse) {
            AbstractMsgDetailFrame dataResponse = (AbstractMsgDetailFrame) object;
            byte msgType = dataResponse.getMsgType();
            buffer.writeByte(msgType);
            if (msgType == DTMessageConstant.dataType_allState || msgType == DTMessageConstant.dataType_stateChange) {
                TotalStateFrame totalStateFrame = (TotalStateFrame)object;
                buffer.writeByte(totalStateFrame.getTotalFrame());
                buffer.writeByte(totalStateFrame.getFrameSerial());
                buffer.writeIntLE(totalStateFrame.getTime());
                buffer.writeShortLE(totalStateFrame.getSwitchInfoList().size());
                totalStateFrame.getSwitchInfoList().forEach(s -> fillSwitchInfo(buffer,s));
                buffer.writeBytes(fixByte_end);
            } else if (msgType == DTMessageConstant.dataType_alarm) {
                AlarmFrame alarmFrame = (AlarmFrame) object;
                buffer.writeByte(alarmFrame.getTotalFrame());
                buffer.writeByte(alarmFrame.getFrameSerial());
                buffer.writeIntLE(alarmFrame.getTime());
                buffer.writeShortLE(alarmFrame.getAlarmList().size());
                alarmFrame.getAlarmList().forEach(s -> fillAlarmInfo(buffer,s));
                buffer.writeBytes(fixByte_end);
            } else {
                log.error("DT序列化错误, 无法识别的DataType:{}", msgType);
                return null;
            }
        } else {
            log.error("DT序列化错误, 无法识别的FrameType:{}",frameType);
            return null;
        }
        byte[] bytes = ByteBufUtil.getBytes(buffer);
        log.debug("序列化得到的字节数组为[{}]", Arrays.toString(bytes));
        return bytes;
    }



    private void fillSwitchInfo(ByteBuf byteBuf, SwitchInfo switchInfo) {
        byteBuf.writeBytes(HexUtil.ipToByteArray(switchInfo.getIp()));
        byteBuf.writeByte(switchInfo.getPortNum());
        switchInfo.getPortInfoList().forEach(portInfo -> {
            byteBuf.writeByte(portInfo.getSlotId());
            byteBuf.writeByte(portInfo.getPortId());
            byteBuf.writeShortLE(portInfo.getState());
        });
    }

    private void fillAlarmInfo(ByteBuf byteBuf, AlarmInfo alarmInfo) {
        byteBuf.writeBytes(HexUtil.ipToByteArray(alarmInfo.getIp()));
        byteBuf.writeByte(alarmInfo.getAlarmType());
        if (alarmInfo.isPortAlarm()) {
            byteBuf.writeByte(alarmInfo.getSlotId());
            byteBuf.writeByte(alarmInfo.getPortId());
        } else {
            byteBuf.writeByte(0);
            byteBuf.writeByte(0);
        }
        byteBuf.writeShortLE(alarmInfo.getAlarmState());
    }

    private SwitchInfo retrieveSwitchInfo(ByteBuf byteBuf) {
        SwitchInfo switchInfo = new SwitchInfo();
        byte[] ipbytes = new byte[4];
        byteBuf.readBytes(ipbytes);
        switchInfo.setIp(HexUtil.byteArrayToIp(ipbytes));
        byte portNum = byteBuf.readByte();
        switchInfo.setPortNum(portNum);
        List<PortInfo> portInfoList = new ArrayList<>();
        for (int i = 0; i < portNum; i++) {
            PortInfo portInfo = new PortInfo();
            portInfo.setSlotId(byteBuf.readByte());
            portInfo.setPortId(byteBuf.readByte());
            portInfo.setState(byteBuf.readShortLE());
            portInfoList.add(portInfo);
        }
        switchInfo.setPortInfoList(portInfoList);
        return switchInfo;
    }

    private AlarmInfo retrieveAlarmInfo(ByteBuf byteBuf) {
        AlarmInfo alarmInfo = new AlarmInfo();
        byte[] ipbytes = new byte[4];
        byteBuf.readBytes(ipbytes);
        alarmInfo.setIp(HexUtil.byteArrayToIp(ipbytes));
        alarmInfo.setAlarmType(byteBuf.readByte());
        alarmInfo.setSlotId(byteBuf.readByte());
        alarmInfo.setPortId(byteBuf.readByte());
        alarmInfo.setAlarmState(byteBuf.readShortLE());
        return alarmInfo;
    }

    public <T> T deserialize(ByteBuf buffer, Class<T> clazz) {
        if (clazz.equals(HeartBeatFrame.class)) {
            HeartBeatFrame heartBeatFrame = new HeartBeatFrame();
            heartBeatFrame.setFrameType(DTMessageConstant.abstractType_heartBeat);
            heartBeatFrame.setTimestamp(buffer.readIntLE());
            byte[] end = new byte[7];
            buffer.readBytes(end);
            if (!Arrays.equals(end,fixByte_7) || buffer.isReadable()) {
                log.error("报文结束信息错误, 丢弃...");
                return null;
            }
            return (T) heartBeatFrame;
        } else if (clazz.equals(AbstractMsgDetailFrame.class)) {
            byte type = buffer.readByte();
            Class<? extends AbstractMsgFrame> msgType = DTMessageConstant.getMsgType(type);
            if (msgType == null) {
                log.error("报文信息子类型错误, 丢弃...");
                return null;
            }
            int total = Byte.toUnsignedInt(buffer.readByte());
            int serial = Byte.toUnsignedInt(buffer.readByte());
            int timeStamp = buffer.readInt();
            int subNum = Short.toUnsignedInt(buffer.readShortLE());
            if (msgType.equals(AlarmFrame.class)) {
                AlarmFrame alarmFrame = new AlarmFrame();
                alarmFrame.setTotalFrame(total);
                alarmFrame.setFrameSerial(serial);
                alarmFrame.setTime(timeStamp);
                List<AlarmInfo> alarmInfoList = new ArrayList<>();
                for (int i = 0; i < subNum; i++) {
                    alarmInfoList.add(retrieveAlarmInfo(buffer));
                }
                alarmFrame.setAlarmList(alarmInfoList);
                byte[] end = new byte[4];
                buffer.readBytes(end);
                if (!Arrays.equals(end,fixByte_end) || buffer.isReadable()) {
                    log.error("报文结束信息错误,丢弃...");
                    return null;
                }
                return (T)alarmFrame;
            } else if (msgType.equals(TotalStateFrame.class)) {
                TotalStateFrame totalStateFrame = new TotalStateFrame();
                totalStateFrame.setTotalFrame(total);
                totalStateFrame.setFrameSerial(serial);
                totalStateFrame.setTime(timeStamp);
                List<SwitchInfo> switchInfoList = new ArrayList<>();
                for (int i = 0; i < subNum; i++) {
                    switchInfoList.add(retrieveSwitchInfo(buffer));
                }
                totalStateFrame.setSwitchInfoList(switchInfoList);
                byte[] end = new byte[4];
                buffer.readBytes(end);
                if (!Arrays.equals(end,fixByte_end) || buffer.isReadable()) {
                    log.error("报文结束信息错误,丢弃...");
                    return null;
                }
                return (T) totalStateFrame;
            }
        }
        return null;
    }
}
