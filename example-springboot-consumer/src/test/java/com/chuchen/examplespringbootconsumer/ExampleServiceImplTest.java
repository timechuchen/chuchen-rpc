package com.chuchen.examplespringbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chuchen
 * @date 2025/5/7 18:24
 */
@SpringBootTest
class ExampleServiceImplTest {
    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    void test() {
        exampleService.test();
    }
}