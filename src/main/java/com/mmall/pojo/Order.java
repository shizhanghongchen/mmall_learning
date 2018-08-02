package com.mmall.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class Order {

    /**
     * 订单主键id
     */
    private Integer id;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单地址id
     */
    private Integer shippingId;

    /**
     * 实际付款金额(单位是元,保留两位小数)
     */
    private BigDecimal payment;

    /**
     * 支付类型(1=在线支付)
     */
    private Integer paymentType;

    /**
     * 运费(单位是元)
     */
    private Integer postage;

    /**
     * 订单状态(0=已取消,10=未付款,20=已付款,40=已发货,50=交易成功,60=交易关闭)
     */
    private Integer status;

    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 发货时间
     */
    private Date sendTime;

    /**
     * 交易完成时间
     */
    private Date endTime;

    /**
     * 交易关闭时间
     */
    private Date closeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 全参构造器
     *
     * @param id          订单主键id
     * @param orderNo     订单号
     * @param userId      用户id
     * @param shippingId  订单地址id
     * @param payment     实际付款金额(单位是元,保留两位小数)
     * @param paymentType 支付类型(1=在线支付)
     * @param postage     运费(单位是元)
     * @param status      订单状态(0=已取消,10=未付款,20=已付款,40=已发货,50=交易完成,60=交易关闭)
     * @param paymentTime 支付时间
     * @param sendTime    发货时间
     * @param endTime     交易完成时间
     * @param closeTime   交易关闭时间
     * @param createTime  创建时间
     * @param updateTime  更新时间
     */
    public Order(Integer id, Long orderNo, Integer userId, Integer shippingId, BigDecimal payment, Integer paymentType, Integer postage, Integer status, Date paymentTime, Date sendTime, Date endTime, Date closeTime, Date createTime, Date updateTime) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.shippingId = shippingId;
        this.payment = payment;
        this.paymentType = paymentType;
        this.postage = postage;
        this.status = status;
        this.paymentTime = paymentTime;
        this.sendTime = sendTime;
        this.endTime = endTime;
        this.closeTime = closeTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public Order() {
        super();
    }

    /**
     * 订单主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 订单主键id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 订单号
     *
     * @return
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * 订单号
     *
     * @param orderNo
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 用户id
     *
     * @return
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 订单地址id
     *
     * @return
     */
    public Integer getShippingId() {
        return shippingId;
    }

    /**
     * 订单地址id
     *
     * @param shippingId
     */
    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    /**
     * 实际付款金额(单位是元,保留两位小数)
     *
     * @return
     */
    public BigDecimal getPayment() {
        return payment;
    }

    /**
     * 实际付款金额(单位是元,保留两位小数)
     *
     * @param payment
     */
    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    /**
     * 支付类型(1=在线支付)
     *
     * @return
     */
    public Integer getPaymentType() {
        return paymentType;
    }

    /**
     * 支付类型(1=在线支付)
     *
     * @param paymentType
     */
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 运费(单位是元)
     *
     * @return
     */
    public Integer getPostage() {
        return postage;
    }

    /**
     * 运费(单位是元)
     *
     * @param postage
     */
    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    /**
     * 订单状态(0=已取消,10=未付款,20=已付款,40=已发货,50=交易完成,60=交易关闭)
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 订单状态(0=已取消,10=未付款,20=已付款,40=已发货,50=交易完成,60=交易关闭)
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 支付时间
     *
     * @return
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 支付时间
     *
     * @param paymentTime
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 发货时间
     *
     * @return
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 发货时间
     *
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 交易完成时间
     *
     * @return
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 交易完成时间
     *
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 交易关闭时间
     *
     * @return
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * 交易关闭时间
     *
     * @param closeTime
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     *
     * @return
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Order is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", shippingId=" + shippingId +
                ", payment=" + payment +
                ", paymentType=" + paymentType +
                ", postage=" + postage +
                ", status=" + status +
                ", paymentTime=" + paymentTime +
                ", sendTime=" + sendTime +
                ", endTime=" + endTime +
                ", closeTime=" + closeTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}