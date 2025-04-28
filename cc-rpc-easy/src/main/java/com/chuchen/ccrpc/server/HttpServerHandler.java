package com.chuchen.ccrpc.server;

import com.chuchen.ccrpc.model.RpcRequest;
import com.chuchen.ccrpc.model.RpcResponse;
import com.chuchen.ccrpc.registry.LocalRegistry;
import com.chuchen.ccrpc.serializer.JdkSerializer;
import com.chuchen.ccrpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chuchen
 * @date 2025/4/28 11:17
 * <p>
 * 请求处理器
 * 对于请求处理器的主要业务流程如下：
 * 1、反序列化请求对象，并且从对象中获取请求的参数
 * 2、根据服务名称从本地注册器中获取到对应的实现类
 * 3、通过反射机制调用对应的放啊，得到返回结果
 * 4、将返回结果进行封装和序列化后，写到响应中
 * <p>
 * 需要注意：不同的web服务器的请求处理器的实现方式不同，
 * 比如Vert.x的实现方式是通过实现Handler<HttpServerRequest>接口来自定义请求处理器。
 * 并且可以通过 request.bodyHandler 异步处理请求
 *
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        // 指定序列化器
        final Serializer serializer = new JdkSerializer();

        // 记录日志
        System.out.println("Received request: " + request.method() + " " + request.uri());

        // 异步处理 HTTP 请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rcpRequest = null;
            try{
                rcpRequest = serializer.Deserializer(bytes, RpcRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 构建响应请求结果
            RpcResponse rpcResponse = new RpcResponse();
            // 如果请求为null，直接返回
            if(rcpRequest == null){
                rpcResponse.setMessage("rcpRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try{
                // 获取要调用的服务的实现类，通过反射调用
                Class<?> implClass = LocalRegistry.getService(rcpRequest.getServiceName());
                Method method = implClass.getMethod(rcpRequest.getMethodName(), rcpRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rcpRequest.getArgs());

                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     InstantiationException e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应请求
            doResponse(request, rpcResponse, serializer);
        });
    }

    /**
     * 响应请求
     *
     * @param request 请求
     * @param rcpResponse 响应
     * @param serializer 序列化器
     */
    void doResponse(HttpServerRequest request, RpcResponse rcpResponse, Serializer serializer) {
        HttpServerResponse response = request.response()
                .putHeader("content-type", "application/json");

        try {
            // 序列化
            byte[] serialized = serializer.Serializer(rcpResponse);
            response.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            response.end(Buffer.buffer());
        }
    }
}
