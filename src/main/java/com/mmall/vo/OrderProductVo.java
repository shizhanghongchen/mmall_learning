package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wb-yxk397023 on 2018/8/11.
 */
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

    /**
     * 订单商品详情
     */
    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    /**
     * 订单商品详情
     */
    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    /**
     * 总价
     */
    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    /**
     * 总价
     */
    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    /**
     * 主图
     */
    public String getImageHost() {
        return imageHost;
    }

    /**
     * 主图
     */
    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
