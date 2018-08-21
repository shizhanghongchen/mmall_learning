package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by wb-yxk397023 on 2018/7/30.
 */
public class Const {

    /**
     * 用户标识
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 用户信息类型校验
     */
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "token_";

    /**
     * 设置redis缓存时间
     */
    public interface RedisCacheExtime {
        // session过期时间
        int REDIS_SESSION_EXTIME = 60 * 30;
    }

    /**
     * 购物车标识
     */
    public interface Cart {
        /**
         * 即购物车选中状态
         */
        int CHECKED = 1;
        /**
         * 购物车中未选中状态
         */
        int UN_CHECKED = 0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 产品排序规则
     */
    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    /**
     * 检验用户类型
     */
    public interface Role {
        /**
         * 普通用户
         */
        int ROLE_CUSTOMER = 0;
        /**
         * 管理员
         */
        int ROLE_ADMIN = 1;
    }

    /**
     * 校验产品状态
     */
    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 订单支付状态
     */
    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");


        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }
    }

    /**
     * 支付宝回调结果
     */
    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    /**
     * 支付类型
     */
    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝");

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 订单支付类型
     */
    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }


        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }

    }

    /**
     * 关闭订单的分布式锁
     */
    public interface REDIS_LOCK {
        String CLOSE_ORDER_TASK_LOCK = "CLOSE_ORDER_TASK_LOCK";
    }
}
