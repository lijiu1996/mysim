package com.lijiawei.simulator.dt.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author Li JiaWei
 * @ClassName: HexUtil
 * @Description:
 * @Date: 2023/1/4 10:25
 * @Version: 1.0
 */
public class HexUtil {

    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static int getTimeStamp() {
        return (int)(System.currentTimeMillis()/1000);
    }

    public static String getTimeFromHex4(String hexTime) {
        String[] s = hexTime.split(" ");
        Integer reduce = Arrays.stream(s).map(s1 -> Integer.valueOf(s1, 16))
                .reduce(0, (r1, r2) -> r1 << 8 | r2);
        LocalDateTime localDateTime = Instant.ofEpochSecond(reduce).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String format = localDateTime.format(dateFormat);
        System.out.println(format);
        return format;
    }

    public static int getIntFromByteArray(byte[] bytes, int start) {
        return getIntFromByteArrayDetail(bytes,start,false);
    }

    public static int getIntFromByteArrayLE(byte[] bytes, int start) {
        return getIntFromByteArrayDetail(bytes, start,true);
    }

    public static int getIntFromByteArrayDetail(byte[] bytes, int start, boolean backward) {
        return (int)getNumberFromByteArrayDetail(bytes,start,4,backward);
    }

    public static long getNumberFromByteArrayDetail(byte[] bytes, int start, int length, boolean backward) {
        int end = start + length - 1;
        if (start < 0 || start >= bytes.length || end < 0 || end >= bytes.length) {
            throw new IllegalArgumentException("参数错误! {" + start + "},{"+ end + "}");
        }

        long reduce = LongStream.
                rangeClosed(start, end).
                map(i -> backward ? start + end - i  : i).
                reduce(0, (r,i) -> r = r << 8 | bytes[(int) i]);
        return reduce;
    }

    public static String prettyByteArray(byte[] array) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : array) {
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() ==1) {
                sb.append('0');
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    public static byte[] ipToByteArray(String ip) {
        String[] split = ip.split("\\.");
        if (split.length != 4)
            throw new IllegalArgumentException("ip地址不合法:" + ip);
        byte[] bytes = new byte[4];
        for (int i = 0; i<4; i++) {
            bytes[i] = Integer.valueOf(split[i]).byteValue();
        }
        return bytes;
    }

    public static String byteArrayToIp(byte[] bytes) {
        return IntStream.range(0,4).mapToObj(i -> String.valueOf((Byte.toUnsignedInt(bytes[i]))))
                .collect(Collectors.joining("."));
    }

    public static void main(String[] args) {
//        getTimeFromHex4("63 b4 ea 72");

        // 测试获取整数数组
//        byte[] bytes = new byte[] {0x0,0x0,0x1,0x2};
//        int intFromByteArray = getIntFromByteArray(bytes, 0);
//        System.out.println(intFromByteArray);
//        byte[] bytes1 = new byte[] {0x4,0x3,0x0,0x0};
//        System.out.println(getIntFromByteArrayLE(bytes1,0));

        // 测试ip与字节数组转换
//        byte[] t = new byte[] {(byte)0xFF,22,33,44};
//        String s = byteArrayToIp(t);
//        System.out.println(s);
//        String s = "0123";
//        byte[] bytes = ipToByteArray(s);
//        System.out.println(Arrays.toString(bytes));



    }


}
