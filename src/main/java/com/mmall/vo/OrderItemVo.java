package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by wb-yxk397023 on 2018/8/11.
 */
@Getter
@Setter
public class OrderItemVo {

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品主图
     */
    private String productImage;

    /**
     * 商品购入时单价
     */
    private BigDecimal currentUnitPrice;

    /**
     * 商品数量
     */
    private Integer quantity;
    
    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 商品添加时间
     */
    private String createTime;
}
