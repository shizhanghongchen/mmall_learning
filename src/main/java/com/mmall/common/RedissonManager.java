package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/21
 */
@Component
@Slf4j
public class RedissonManager {

    /**
     * 创建Config对象
     */
    private Config config = new Config();

    /**
     * 初始化redisson对象
     */
    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    /**
     * 获取redis1Ip
     */
    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");

    /**
     * 获取redis1Port端口号
     */
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    /**
     * 获取redis2Ip
     */
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");

    /**
     * 获取redis2Port端口号
     */
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    /**
     * 初始化方法
     */
    @PostConstruct
    private void init() {
        try {
            /**
             * 加载单服务的SingleServer
             */
            config.useSingleServer().setAddress(new StringBuilder().append(redis1Ip).append(":").append(redis1Port).toString());

            /**
             * redisson赋值
             */
            redisson = (Redisson) Redisson.create(config);

            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson init error", e);
        }
    }
}
