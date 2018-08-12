package com.mmall.pojo;

import lombok.*;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    /**
     * 购物车主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 是否勾选(1=已勾选,0=未勾选)
     */
    private Integer checked;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}