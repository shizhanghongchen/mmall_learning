package com.mmall.vo;

import java.math.BigDecimal;

/**
 * Created by wb-yxk397023 on 2018/8/11.
 */
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

    /**
     * 订单号
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * 订单号
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 商品id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 商品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 商品主图
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * 商品主图
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * 商品购入时单价
     */
    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    /**
     * 商品购入时单价
     */
    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    /**
     * 商品数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 商品数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 商品总价
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * 商品总价
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 商品添加时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 商品添加时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
