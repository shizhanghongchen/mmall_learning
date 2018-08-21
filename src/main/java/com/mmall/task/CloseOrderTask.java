package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时关单
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/20
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    /**
     * 订单关单的逻辑(V1.0)
     * 每一分钟执行一次(每个一分钟的整数倍)
     */
    // @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        // 超时时间
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    /**
     * 订单关单的逻辑(V2.0)
     * 每一分钟执行一次(每个一分钟的整数倍)
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        // 设置超时时间
        long lockTime = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        // 设置分布式锁的key-value
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()));
        // 如果返回值不为空且等于1,表示设置成功获取锁
        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 执行关单逻辑
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获取分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    /**
     * 设置分布式锁的有效期以及锁的释放
     *
     * @param lockName
     */
    private void closeOrder(String lockName) {
        // 设置分布式锁的有效期
        RedisShardedPoolUtil.expire(lockName, 5);
        log.info("获取{}, ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        // 获取超时时间
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        // 关闭订单
        iOrderService.closeOrder(hour);
        // 释放锁
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{}, ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("========================================");
    }
}
