package com.mmall.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class OrderItem {

    /**
     * 订单子表主键id
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
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片地址
     */
    private String productImage;

    /**
     * 生成订单时的商品单价(单位十元,保留两位小数)
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
     * @param id               订单子表主键id
     * @param userId           用户id
     * @param orderNo          订单号
     * @param productId        商品id
     * @param productName      商品名称
     * @param productImage     商品图片地址
     * @param currentUnitPrice 生成订单时的商品单价(单位十元,保留两位小数)
     * @param quantity         商品数量
     * @param totalPrice       商品总价
     * @param createTime       创建时间
     * @param updateTime       更新时间
     */
    public OrderItem(Integer id, Integer userId, Long orderNo, Integer productId, String productName, String productImage, BigDecimal currentUnitPrice, Integer quantity, BigDecimal totalPrice, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.currentUnitPrice = currentUnitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public OrderItem() {
        super();
    }

    /**
     * 订单子表主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 订单子表主键id
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
     * 商品id
     *
     * @return
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 商品id
     *
     * @param productId
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 商品名称
     *
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 商品名称
     *
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * 商品图片地址
     *
     * @return
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * 商品图片地址
     *
     * @param productImage
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    /**
     * 生成订单时的商品单价(单位是元,保留两位小数)
     *
     * @return
     */
    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    /**
     * 生成订单时的商品单价(单位是元,保留两位小数)
     *
     * @param currentUnitPrice
     */
    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    /**
     * 商品数量
     *
     * @return
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 商品数量
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 商品总价
     *
     * @return
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * 商品总价
     *
     * @param totalPrice
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
     * OrderItem is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderNo=" + orderNo +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", currentUnitPrice=" + currentUnitPrice +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}