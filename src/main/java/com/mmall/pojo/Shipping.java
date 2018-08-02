package com.mmall.pojo;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class Shipping {

    /**
     * 地址主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货姓名
     */
    private String receiverName;

    /**
     * 收货固定电话
     */
    private String receiverPhone;

    /**
     * 收货移动电话
     */
    private String receiverMobile;

    /**
     * 省份
     */
    private String receiverProvince;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     * 区/县
     */
    private String receiverDistrict;

    /**
     * 详细地址
     */
    private String receiverAddress;

    /**
     * 邮编
     */
    private String receiverZip;

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
     * @param id               地址主键id
     * @param userId           用户id
     * @param receiverName     收货姓名
     * @param receiverPhone    收货固定电话
     * @param receiverMobile   收货移动电话
     * @param receiverProvince 省份
     * @param receiverCity     城市
     * @param receiverDistrict 区/县
     * @param receiverAddress  详细地址
     * @param receiverZip      邮编
     * @param createTime       创建时间
     * @param updateTime       更新时间
     */
    public Shipping(Integer id, Integer userId, String receiverName, String receiverPhone, String receiverMobile, String receiverProvince, String receiverCity, String receiverDistrict, String receiverAddress, String receiverZip, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverMobile = receiverMobile;
        this.receiverProvince = receiverProvince;
        this.receiverCity = receiverCity;
        this.receiverDistrict = receiverDistrict;
        this.receiverAddress = receiverAddress;
        this.receiverZip = receiverZip;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public Shipping() {
        super();
    }

    /**
     * 地址主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 地址主键id
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
     * 收货姓名
     *
     * @return
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 收货姓名
     *
     * @param receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * 收货固定电话
     *
     * @return
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * 收货固定电话
     *
     * @param receiverPhone
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    /**
     * 收货移动电话
     *
     * @return
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * 收货移动电话
     *
     * @param receiverMobile
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }

    /**
     * 省份
     *
     * @return
     */
    public String getReceiverProvince() {
        return receiverProvince;
    }

    /**
     * 省份
     *
     * @param receiverProvince
     */
    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince == null ? null : receiverProvince.trim();
    }

    /**
     * 城市
     *
     * @return
     */
    public String getReceiverCity() {
        return receiverCity;
    }

    /**
     * 城市
     *
     * @param receiverCity
     */
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity == null ? null : receiverCity.trim();
    }

    /**
     * 区/县
     *
     * @return
     */
    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    /**
     * 区/县
     *
     * @param receiverDistrict
     */
    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict == null ? null : receiverDistrict.trim();
    }

    /**
     * 详细地址
     *
     * @return
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * 详细地址
     *
     * @param receiverAddress
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    /**
     * 邮编
     *
     * @return
     */
    public String getReceiverZip() {
        return receiverZip;
    }

    /**
     * 邮编
     *
     * @param receiverZip
     */
    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip == null ? null : receiverZip.trim();
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
     * Shipping is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Shipping{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverProvince='" + receiverProvince + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverDistrict='" + receiverDistrict + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}