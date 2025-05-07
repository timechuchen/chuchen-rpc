package com.chuchen.ccrpc.fault.tolerant;

import com.chuchen.ccrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author chuchen
 * @date 2025/5/7 16:11
 * @description 静默处理异常 - 容错策略（只是记录日志，然后返回正常地响应对象，好像没有出现错误）
 */
@Slf4j
public class FailSafeTolerant implements TolerantStrategy{

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常：{}", e.getMessage());
        return new RpcResponse();
    }
}
