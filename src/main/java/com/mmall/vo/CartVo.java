package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wb-yxk397023 on 2018/8/6.
 */
@Getter
@Setter
public class CartVo {

    /**
     * 私有对象
     */
    private List<CartProductVo> cartProductVoList;

    /**
     * 购物车总价
     */
    private BigDecimal cartTotalPrice;

    /**
     * 是否全选
     */
    private Boolean allChecked;

    /**
     * 图片地址
     */
    private String imageHost;
}
