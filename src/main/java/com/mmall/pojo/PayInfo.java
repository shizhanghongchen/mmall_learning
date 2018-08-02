package com.mmall.pojo;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class PayInfo {

    /**
     * 支付信息主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 支付平台(1=支付宝,2=微信)
     */
    private Integer payPlatform;

    /**
     * 支付宝支付流水号
     */
    private String platformNumber;

    /**
     * 支付宝支付状态
     */
    private String platformStatus;

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
     * @param id             支付信息主键id
     * @param userId         用户id
     * @param orderNo        订单号
     * @param payPlatform    支付平台(1-支付宝,2=微信)
     * @param platformNumber 支付宝流水号
     * @param platformStatus 支付宝支付状态
     * @param createTime     创建时间
     * @param updateTime     更新时间
     */
    public PayInfo(Integer id, Integer userId, Long orderNo, Integer payPlatform, String platformNumber, String platformStatus, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.payPlatform = payPlatform;
        this.platformNumber = platformNumber;
        this.platformStatus = platformStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public PayInfo() {
        super();
    }

    /**
     * 支付信息主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 支付信息主键id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 支付平台(1=支付宝,2=微信)
     *
     * @return
     */
    public Integer getPayPlatform() {
        return payPlatform;
    }

    /**
     * 支付平台(1=支付宝,2=微信)
     *
     * @param payPlatform
     */
    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    /**
     * 支付宝流水号
     *
     * @return
     */
    public String getPlatformNumber() {
        return platformNumber;
    }

    /**
     * 支付宝流水号
     *
     * @param platformNumber
     */
    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber == null ? null : platformNumber.trim();
    }

    /**
     * 支付宝支付状态
     *
     * @return
     */
    public String getPlatformStatus() {
        return platformStatus;
    }

    /**
     * 支付宝支付状态
     *
     * @param platformStatus
     */
    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus == null ? null : platformStatus.trim();
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
     * PayInfo is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "PayInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderNo=" + orderNo +
                ", payPlatform=" + payPlatform +
                ", platformNumber='" + platformNumber + '\'' +
                ", platformStatus='" + platformStatus + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}