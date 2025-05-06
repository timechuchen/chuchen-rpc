package com.chuchen.ccrpc.protocol;

import lombok.Getter;

/**
 * @author chuchen
 * @date 2025/4/30 14:27
 * <br>
 * 协议消息类型枚举：包括请求、响应、心跳、其他。代码如下
 */
@Getter
public enum ProtocolMessageTypeEnum {

    REQUEST(0),
    RESPONSE(1),
    HEART_BEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取枚举
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum value : ProtocolMessageTypeEnum.values()) {
            if (value.key == key) {
                return value;
            }
        }
        return null;
    }
}
