package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wb-yxk397023 on 2018/8/11.
 */
@Getter
@Setter
public class OrderProductVo {
    /**
     * 订单商品详情
     */
    private List<OrderItemVo> orderItemVoList;

    /**
     * 总价
     */
    private BigDecimal productTotalPrice;

    /**
     * 主图
     */
    private String imageHost;
}
