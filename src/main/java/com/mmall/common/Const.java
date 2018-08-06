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
     * 购物车标识
     */
    public interface Cart {
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车中未选中状态

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
        // 普通用户
        int ROLE_CUSTOMER = 0;
        // 管理员
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
}
