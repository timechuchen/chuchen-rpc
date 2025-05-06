package com.chuchen.ccrpc.serializer;

import java.io.IOException;

/**
 * @author chuchen
 * @date 2025/4/28 10:56
 * 序列化接口
 */
public interface Serializer {

    /**
     * 序列化：这方法的最终目的其实就是将Java对象转化为字节数组
     * @param object 需要序列化的对象
     * @return
     * @param <T> 类型
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化: 这方法的最终目的其实就是将字节数组转化为Java对象
     * @param bytes 需要反序列化的字节数组
     * @param clazz 反序列化后的类型
     * @param <T> 类型
     * @return
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
