package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/18
 */
public class RedisShardedPool {

    /**
     * 连接池超时时间
     */
    private static final Integer TIME_OUT = 1000 * 2;

    /**
     * Sharded Jedis连接池
     */
    private static ShardedJedisPool pool;

    /**
     * 最大连接数
     */
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));

    /**
     * 在jedisPool中最大的idle状态(空闲)的jedis实例个数
     */
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));

    /**
     * 在jedisPool中最小的idle状态(空闲)的jedis实例个数
     */
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));

    /**
     * 在borrow一个jedis实例的时候,是否要进行验证(如果赋值为true,则得到的jedis实例肯定是可以用的)
     */
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));

    /**
     * 在return一个jedis实例的时候,是否要进行验证(如果赋值为true,则放回jedisPool的jedis实例肯定是可以用的)
     */
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

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
     * 初始化连接池(无返回值)
     */
    private static void initPool() {
        // 配置JedisPool
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接
        config.setMaxTotal(maxTotal);
        // 设置最大空闲
        config.setMaxIdle(maxIdle);
        // 设置最小空闲
        config.setMinIdle(minIdle);
        // 设置borrow
        config.setTestOnBorrow(testOnBorrow);
        // 设置return
        config.setTestOnReturn(testOnReturn);
        // 设置连接耗尽时是否阻塞(true表示阻塞直到超时,源码默认为true;false会抛出异常)
        config.setBlockWhenExhausted(true);
        // 初始化连接池info1
        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, TIME_OUT);
        // 初始化连接池info2
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, TIME_OUT);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    /**
     * 加载时执行初始化操作
     */
    static {
        initPool();
    }

    /**
     * 从连接池中获取ShardedJedis实例
     *
     * @return
     */
    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    /**
     * 将连接放回到连接池中(未损坏的连接)
     *
     * @param jedis
     */
    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    /**
     * 将连接放回到连接池中(已损坏的连接)
     *
     * @param jedis
     */
    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    /**
     * 测试连接
     *
     * @param args
     */
    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i = 0; i < 10; i++) {
            jedis.set("key" + i, "value" + i);
        }
        returnResource(jedis);
        // 临时调用，销毁连接池中的所有连接
        // pool.destroy();
        System.out.println("program is end");
    }
}
