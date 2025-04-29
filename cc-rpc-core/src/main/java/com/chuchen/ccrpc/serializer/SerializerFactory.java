package com.chuchen.ccrpc.serializer;

import com.chuchen.ccrpc.spi.SpiLoader;

/**
 * @author chuchen
 * @date 2025/4/29 10:08
 * <br>
 * 序列化工厂：用于获取序列化实例对象
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取序列化器
     *
     * @param serializerKey 序列化器key
     * @return 序列化器
     */
    public static Serializer getInstance(String serializerKey) {
        return SpiLoader.getInstance(Serializer.class, serializerKey);
    }
}
