package com.chuchen.ccrpc.serializer;

import com.chuchen.ccrpc.model.RpcRequest;
import com.chuchen.ccrpc.model.RpcResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author chuchen
 * @date 2025/4/29 09:15
 * <br>
 * JSON 序列化器：Json 序列化相对复杂一点，因为要考虑数据兼容问题，比如 Object 对象在序列化后会丢失类型
 */
public class JsonSerializer implements Serializer{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] Serializer(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T Deserializer(byte[] bytes, Class<T> clazz) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, clazz);
        // 就是这里要注意一下类型的转化
        if(obj instanceof RpcRequest) {
            return handleRpcRuquest((RpcRequest) obj, clazz);
        }
        if(obj instanceof RpcResponse) {
            return handleRpcResponse((RpcResponse) obj, clazz);
        }
        return null;
    }

    /**
     * 由于 Object 的原始对象会被擦除，所以在反序列化的时候会被 LinkedHashMap 替换掉，所以这里需要做一下类型转化
     * @param type 类型
     * @param rpcRequest RPC 请求
     */
    private <T> T handleRpcRuquest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterType = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        // 循环处理每一个参数的类型
        for (int i = 0; i < parameterType.length; i++) {
            Class<?> clazz = parameterType[i];
            // 如果类型不同就处理一下类型
            if(!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }

        return type.cast(rpcRequest);
    }

    /**
     * 由于 Object 的原始对象会被擦除，所以在反序列化的时候会被 LinkedHashMap 替换掉，所以这里需要做一下类型转化
     * @param type 类型
     * @param rpcResponse RPC 响应
     */
    private <T> T handleRpcResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        // 处理响应数据
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}
