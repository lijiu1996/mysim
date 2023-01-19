package com.lijiawei.simulator.dt.serializer;

import cn.hutool.core.util.RandomUtil;
import com.google.common.base.Joiner;
import com.lijiawei.simulator.dt.bean.dto.AlarmFrame;
import com.lijiawei.simulator.dt.bean.dto.HeartBeatFrame;
import com.lijiawei.simulator.dt.bean.dto.TotalStateFrame;
import com.lijiawei.simulator.dt.bean.pojo.AlarmInfo;
import com.lijiawei.simulator.dt.bean.pojo.PortInfo;
import com.lijiawei.simulator.dt.bean.pojo.SwitchInfo;
import com.lijiawei.simulator.dt.constant.DTMessageConstant;
import com.lijiawei.simulator.dt.handler.*;
import com.lijiawei.simulator.dt.service.impl.AlarmInfoServiceImpl;
import com.lijiawei.simulator.dt.service.impl.SwitchInfoServiceImpl;
import com.lijiawei.simulator.dt.util.HexUtil;
import com.lijiawei.simulator.dt.util.MockUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Li JiaWei
 * @ClassName: ByteBufBasedSerializerTest
 * @Description:    netty 单元测试
 * @Date: 2023/1/4 10:07
 * @Version: 1.0
 */
@SpringBootTest
class ByteBufBasedSerializerTest {

    @Autowired
    private DtDecoder dtDecoder;

    private EmbeddedChannel testSplitChannel() {
        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 1440, 1, 4, 1, 0, false);
        return new EmbeddedChannel(decoder,dtDecoder,
                new HeartBeatHandler(),new AlarmHandler(), new TotalStateHandler(),
                new DtEncoder(),new TotalFrameEncoder());
    }

    private EmbeddedChannel testChannel() {
        return new EmbeddedChannel(dtDecoder,new DtEncoder());
    }

    @Autowired
    private SwitchInfoServiceImpl switchInfoService;

    @Autowired
    private AlarmInfoServiceImpl alarmInfoService;

    @Test
    public void nettyTestDemo() {

        generateSwitchInfoList(1);

        EmbeddedChannel channel = testChannel();
//        HeartBeatFrame heartBeatFrame = new HeartBeatFrame();
//        heartBeatFrame.setFrameType(DTMessageConstant.abstractType_heartBeat);
//        heartBeatFrame.setTimestamp(HexUtil.getTimeStamp());
//        channel.writeOutbound(heartBeatFrame);
        TotalStateFrame totalStateFrame = new TotalStateFrame();
        totalStateFrame.setTotalFrame(2);
        totalStateFrame.setFrameSerial(1);
        totalStateFrame.setTime(HexUtil.getTimeStamp());
        channel.writeOutbound(totalStateFrame);
        channel.finish();
        ByteBuf o = (ByteBuf)channel.readOutbound();
        byte[] bytes = ByteBufUtil.getBytes(o);
        String s = ByteBufUtil.prettyHexDump(o);
        System.out.println(s);
    }

    // 能否正确解析粘包问题
    @Test
    public void testSplicing() {
//        EmbeddedChannel embeddedChannel = testChannel();
        EmbeddedChannel embeddedChannel = testSplitChannel();
        HeartBeatFrame heartBeatFrame = new HeartBeatFrame();
        System.out.println("now time ======= " + heartBeatFrame.getTimestamp());
        embeddedChannel.writeOutbound(heartBeatFrame);
        HeartBeatFrame heartBeatFrame1 = new HeartBeatFrame();
        embeddedChannel.writeOutbound(heartBeatFrame1);
//        embeddedChannel.finish();
        ByteBuf o = embeddedChannel.readOutbound();
        ByteBuf o2 = embeddedChannel.readOutbound();
//        System.out.println(ByteBufUtil.prettyHexDump(o));
//        System.out.println(ByteBufUtil.prettyHexDump(o2));

        byte[] bytes = new byte[] {1,2,3,4};

        ByteBuf buffer = embeddedChannel.alloc().buffer();
        buffer.writeBytes(o).writeBytes(o2).writeBytes(bytes);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        embeddedChannel.writeInbound(buffer);
//        embeddedChannel.writeInbound(copy2);
        Object o1 = embeddedChannel.readInbound();
        System.out.println(o1);
    }

    @Test
    public void testHeartFrame() {
        EmbeddedChannel embeddedChannel = testChannel();
        HeartBeatFrame heartBeatFrame = new HeartBeatFrame();
        System.out.println("now time ======= " + heartBeatFrame.getTimestamp());
        embeddedChannel.writeOutbound(heartBeatFrame);
//        embeddedChannel.finish();
        ByteBuf o = embeddedChannel.readOutbound();
//        System.out.println(ByteBufUtil.prettyHexDump(o));

        embeddedChannel.writeInbound(o);
        Object o1 = embeddedChannel.readInbound();
        System.out.println(o1);
    }

    @Test
    public void testAlarmFrame() {
        EmbeddedChannel embeddedChannel = testChannel();
        AlarmFrame alarmFrame = new AlarmFrame(generateAlarmInfoList(2));
        alarmFrame.setTotalFrame(1);
        alarmFrame.setFrameSerial(1);

        System.out.println(alarmFrame);
        embeddedChannel.writeOutbound(alarmFrame);
        ByteBuf o = embeddedChannel.readOutbound();
        System.out.println(ByteBufUtil.prettyHexDump(o));
        System.out.println();

    }

    @Test
    public void totalStateFrame() {
//        EmbeddedChannel embeddedChannel = testChannel();
        EmbeddedChannel embeddedChannel = testSplitChannel();
        TotalStateFrame totalStateFrame = new TotalStateFrame(generateSwitchInfoList(21));
        System.out.println(totalStateFrame);
        embeddedChannel.writeOutbound(totalStateFrame);
        ByteBuf buf = null;
        ByteBuf out = embeddedChannel.alloc().buffer();
        while ((buf = embeddedChannel.readOutbound()) != null) {
            out.writeBytes(buf);
        }
        System.out.println(ByteBufUtil.prettyHexDump(out));
        embeddedChannel.writeInbound(out);
        embeddedChannel.readInbound();
    }


    private List<SwitchInfo> generateSwitchInfoList(int switchNum) {
        List<SwitchInfo> resultList = new ArrayList<>();
        int portNum = 32;
        IntStream.range(1,switchNum+1).forEach(sid ->{
            SwitchInfo switchInfo = new SwitchInfo();
            switchInfo.setIp(MockUtil.getMockIp());
            switchInfo.setSwitchId(sid);
            switchInfo.setPortNum(portNum);
            List<PortInfo> portInfos = new ArrayList<>();
            switchInfo.setPortInfoList(portInfos);
            resultList.add(switchInfo);
            IntStream.range(1,portNum+1).forEach(pid -> {
                PortInfo portInfo = new PortInfo();
                portInfo.setPortId(pid);
                portInfo.setSlotId(pid % 2 + 1);
                portInfo.setState(DTMessageConstant.portState_link_up);
                portInfos.add(portInfo);
            });
        });
//        switchInfoService.saveSwitchInfo(resultList);
        return resultList;
    }

    private List<AlarmInfo> generateAlarmInfoList(int alarmNum) {
        List<AlarmInfo> resultList = new ArrayList<>();
        IntStream.range(1,alarmNum+1).forEach(id -> {
            AlarmInfo alarmInfo = new AlarmInfo();
            alarmInfo.setIp(MockUtil.getMockIp());
            alarmInfo.setPortAlarm(true);
            byte alarmType = (byte) RandomUtil.randomInt(1,20);
            alarmInfo.setAlarmType(alarmType);
            byte portIndex = (byte) RandomUtil.randomInt(1, 33);
            alarmInfo.setPortId(portIndex);
            alarmInfo.setSlotId((byte) (portIndex % 2 + 1));
            alarmInfo.setAlarmState((short) DTMessageConstant.alarmState_alarm);
            resultList.add(alarmInfo);
        });
        return resultList;
    }

    /**
     *  save方法不需要定义@TableId
     *  仅有saveOrUpdate方法需要定义主键
     *  -->   手动控制插入来解决复合主键问题
     */
    @Test
    public void insertPortInfo() {
        List<SwitchInfo> switchInfos = generateSwitchInfoList(1);
        switchInfoService.saveSwitchInfo(switchInfos);
        // 3 6 3 6 3
    }

    // 对于byte数组相关工具类的研究
    @Test
    public void testChar() {
        // Character.digit 将字符表示的char 按照对应的进制转换为数字
        System.out.println(Character.digit('1',10));
        // Integer.toHexString 表示将整数转换成对应的进制的字符串捏

//        byte[] xt = {0x08,0x11,0,0,0,0x68};
//        String cli ="";
//        for(int i=0;i<xt.length;i++){
//            if(i==0){
//                cli =Integer.toHexString(xt[i]&0xff);
//            }else if(i>0){
//                cli=cli+"-"+Integer.toHexString(xt[i]&0xff);
//            }
//        }
//        System.out.println(cli);
//
//        String cl2 = "";
//        for (byte b : xt) {
//            cl2 += Integer.toString(b) + "-";
//        }
//        System.out.println(cl2);
    }

    @Test
    public void testAssert() {
        // 尝试使用一下JDK的断言机制
        List<String> strings = Arrays.asList("spring", "java", "junit", "test");
        String join = Joiner.on("##").join(strings);
        StringBuilder today = new StringBuilder("today");
        today.append("cba");
        Joiner.on("#").appendTo(today, strings);
        Assertions.assertEquals(join,"spring##java##junit##test");
        Assertions.assertEquals(today.toString(),"todaycbaspring#java#junit#test");
    }
}