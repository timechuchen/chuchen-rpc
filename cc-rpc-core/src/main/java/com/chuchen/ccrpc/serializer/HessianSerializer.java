package com.chuchen.ccrpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author chuchen
 * @date 2025/4/29 10:01
 * <br>
 * Hessian 序列化器
 */
public class HessianSerializer implements Serializer{

    @Override
    public <T> byte[] Serializer(T object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(byteArrayOutputStream);
        ho.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T Deserializer(byte[] bytes, Class<T> clazz) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        HessianInput hi = new HessianInput(byteArrayInputStream);
        return (T) hi.readObject(clazz);
    }
}
