package com.lijiawei.simulator.dt.serializer;

/**
 * @author Li JiaWei
 * @ClassName: MySerializer
 * @Description:
 * @Date: 2023/1/3 11:14
 * @Version: 1.0
 */
public interface MySerializer {

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
