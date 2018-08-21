package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.common.RedissonManager;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedissonManager redissonManager;

    /**
     * 针对分布式锁V2.0版本特殊情况下死锁的解决方案(如果使用shutdown方式重启容器此方法将会被调用,分布式锁将会统一得到处理)
     * 如果数据量较大会占用大量的时间影响性能且使用kill的方式重启容器此方法不会被调用
     */
    // @PreDestroy
    public void delLock() {
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

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
    // @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        // 设置超时时间
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        // 设置分布式锁的key-value
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
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
     * 订单关单的逻辑(V3.0)
     * 每一分钟执行一次(每个一分钟的整数倍)
     */
    // @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        // 设置超时时间
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        // 设置分布式锁的key-value
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        // 如果返回值不为空且等于1,表示设置成功获取锁
        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 执行关单逻辑
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            // 如果未获取到锁则继续使用时间戳进行判断(看是否可以重置或获取到锁)
            // 根据key拿到锁的value
            String lockValueStr = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            // 判断是否满足重置条件(如果当前获取到的锁的value不为空且小于当前的时间则条件成立,执行重置以及获取锁的逻辑)
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                // 重置锁(集群环境下应该重新声明变量接收)
                String getSetResult = RedisShardedPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                // 再次使用当前时间戳getset;返回key的旧值,使用旧值进行判断,看是否可以获取锁;当key没有旧值(即key不存在时)返回nil,获取锁
                if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
                    // 真正获取到锁,执行关单逻辑
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获取到分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }

    /**
     * 订单关单的逻辑(V4.0)
     * 每一分钟执行一次(每个一分钟的整数倍)
     * redisson框架实现
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV4() {
        // 获取可重入锁
        RLock rLock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        // 标记
        boolean getLock = false;
        try {
            // 判断是否获取锁是否成功(waitTime时间建议设置为0解决锁竞争问题)
            if (getLock = rLock.tryLock(0, 5, TimeUnit.SECONDS)) {
                log.info("Redisson获取到分布式锁:{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
                iOrderService.closeOrder(hour);
            } else {
                log.info("Redisson没有获取到分布式锁:{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取异常", e);
        } finally {
            if (!getLock) {
                return;
            }
            // 释放锁
            rLock.unlock();
            log.info("Redisson分布式锁获取释放");
        }
    }

    /**
     * 设置分布式锁的有效期以及锁的释放
     *
     * @param lockName
     */
    private void closeOrder(String lockName) {
        // 设置分布式锁的有效期,防止死锁
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
