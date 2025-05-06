package com.chuchen.ccrpc.protocol;

import lombok.Getter;

/**
 * @author chuchen
 * @date 2025/4/30 14:21
 * <br>
 * 协议消息枚举，暂时只定义成功、请求失败和相应失败
 */
@Getter
public enum ProtocolMessageStatusEnum {

    OK("ok", 0),
    BAD_REQUEST("badRequest", 40),
    BAD_RESPONSE("badResponse", 50);

    private final String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }
}
