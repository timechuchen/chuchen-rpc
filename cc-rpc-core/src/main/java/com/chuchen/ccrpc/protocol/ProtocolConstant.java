package com.chuchen.ccrpc.protocol;

/**
 * @author chuchen
 * @date 2025/4/30 14:18
 * <br>
 * 协议常量：记录了和定义协议有关的关键信息，比如消息头长度、魔数、版本号
 */
public interface ProtocolConstant {

    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本
     */
    byte PROTOCOL_VERSION = 0x1;
}
