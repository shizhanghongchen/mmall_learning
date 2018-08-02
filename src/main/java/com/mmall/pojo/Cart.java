package com.mmall.pojo;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class Cart {

    /**
     * 购物车主键id
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
     * 商品数量
     */
    private Integer quantity;

    /**
     * 是否勾选(1=已勾选,0=未勾选)
     */
    private Integer checked;

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
     * @param id         主键id
     * @param userId     用户id
     * @param productId  商品id
     * @param quantity   商品数量
     * @param checked    是否勾选(1=已勾选,0=未勾选)
     * @param createTime 创建时间
     * @param updateTime 更新时间
     */
    public Cart(Integer id, Integer userId, Integer productId, Integer quantity, Integer checked, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public Cart() {
        super();
    }

    /**
     * 主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
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
     * 是否选中(1=已选中,0=未选中)
     *
     * @return
     */
    public Integer getChecked() {
        return checked;
    }

    /**
     * 是否选中(1=已选中,0=未选中)
     *
     * @param checked
     */
    public void setChecked(Integer checked) {
        this.checked = checked;
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
     * Cart is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", checked=" + checked +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}