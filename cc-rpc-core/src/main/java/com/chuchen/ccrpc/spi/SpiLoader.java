package com.chuchen.ccrpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.chuchen.ccrpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chuchen
 * @date 2025/4/29 10:27
 * <br>
 * SpiLoader 加载器：相当于一个工具类，提供了读取配置加载类并且实现的方法
 * <br>
 * 关键实现如下：<br>
 * 1、用于存储已经加载的配置信息（键名 - 实现类）
 * 2、扫描指定路径的每一个配置文件，获取到 键名 - 实现类 的键值对，存储到 Map 中
 * 3、定义获取实例的方法，根据用户传入的接口和键名，从 Map 中找到对应的实现类，然后通过反射获取到实现类的对象。
 * 可以维护一个对象实例缓存，创建过一次的对象实例可直接从缓存中获取
 */
@Slf4j
public class SpiLoader {

    /**
     * 存储已经加载的类：接口名 -> (key -> 实现类))
     */
     private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    /**
     * 对象实例缓存类（避免重复 new）：类路径 -> 对象实例 单例模式
     */
    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    /**
     * 系统 SPI 目录
     */
    private static final String SPI_SYSTEM_PATH = "META-INF/rpc/system/";

    /**
     * 用户 SPI 目录
     */
    private static final String SPI_USER_PATH = "META-INF/rpc/custom/";

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DIRS = new String[]{SPI_SYSTEM_PATH, SPI_USER_PATH};

    /**
     * 动态加载所有的类列表
     */
    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    /**
     * 加载所有类
     */
    public static void loadAll() {
        log.info("加载所有 SPI");
        for(Class<?> aCalss : LOAD_CLASS_LIST) {
            load(aCalss);
        }
    }

    /**
     * 获取某个接口的实例
     */
    public static <T> T getInstance(Class<?> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if(keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
        }
        if(!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型", tClassName, key));
        }
        // 获取到要加载到实现类
        Class<?> implClass = keyClassMap.get(key);
        // 从缓存中获取对象实例
        String implClassName = implClass.getName();
        if(!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, implClass.newInstance());
            }catch (Exception e) {
                String errorMsg = String.format("%s 实例化失败", e.getMessage());
                throw new RuntimeException(errorMsg);
            }
        }

        return (T) instanceCache.get(implClassName);
    }

    /**
     * 加载某个类
     */
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为 {} 的 SPI", loadClass.getName());
        // 扫描路径，用户自定义的 SPI 级别高于系统的 SPI
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        for(String scanDir : SCAN_DIRS) {
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            for(URL resource : resources) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        String[] split = line.split("=");
                        if (split.length > 1) {
                            String key = split[0];
                            String className = split[1];
                            keyClassMap.put(key, Class.forName(className));
                        }
                    }
                }catch (Exception e) {
                    log.error("加载 SPI 失败", e);
                }
            }
        }
        loaderMap.put(loadClass.getName(), keyClassMap);
        return keyClassMap;
    }
}
