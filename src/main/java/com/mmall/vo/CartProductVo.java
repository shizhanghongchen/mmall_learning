package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by wb-yxk397023 on 2018/8/6.
 */
@Getter
@Setter
public class CartProductVo {

    /**
     * 主键
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
     * 购物车中此商品的数量
     */
    private Integer quantity;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品副标题
     */
    private String productSubtitle;

    /**
     * 商品主图
     */
    private String productMainImage;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品状态
     */
    private Integer productStatus;

    /**
     * 商品总价
     */
    private BigDecimal productTotalPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 此商品是否勾选
     */
    private Integer productChecked;

    /**
     * 限制数量信息的标识位
     */
    private String limitQuantity;
}
