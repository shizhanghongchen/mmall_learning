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
public class OrderVo {
    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 总价
     */
    private BigDecimal payment;

    /**
     * 支付类型
     */
    private Integer paymentType;

    /**
     * 支付类型描述
     */
    private String paymentTypeDesc;

    /**
     * 邮费
     */
    private Integer postage;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 付款时间
     */
    private String paymentTime;

    /**
     * 发货时间
     */
    private String sendTime;

    /**
     * 交易结束时间
     */
    private String endTime;

    /**
     * 订单关闭时间
     */
    private String closeTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 订单明细
     */
    private List<OrderItemVo> orderItemVoList;

    /**
     * 主图
     */
    private String imageHost;

    /**
     * 收货地址id
     */
    private Integer shippingId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货信息明细
     */
    private ShippingVo shippingVo;
}
