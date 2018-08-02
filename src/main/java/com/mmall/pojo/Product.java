package com.mmall.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class Product {

    /**
     * 商品主键id
     */
    private Integer id;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品副标题
     */
    private String subtitle;

    /**
     * 商品主图url(相对地址)
     */
    private String mainImage;

    /**
     * 商品附图(json格式,扩展用)
     */
    private String subImages;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 商品单价(单位是元,保留两位小数)
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品状态(1=在售,2=下架,3=删除)
     */
    private Integer status;

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
     * @param id         商品主键id
     * @param categoryId 类目id
     * @param name       商品名称
     * @param subtitle   商品副标题
     * @param mainImage  商品主图url(相对地址)
     * @param subImages  商品附图(json格式,扩展用)
     * @param detail     商品详情
     * @param price      商品单价(单位是元,保留两位小数)
     * @param stock      库存数量
     * @param status     商品状态(1=在售,2=下架,3=删除)
     * @param createTime 创建时间
     * @param updateTime 更新时间
     */
    public Product(Integer id, Integer categoryId, String name, String subtitle, String mainImage, String subImages, String detail, BigDecimal price, Integer stock, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.subtitle = subtitle;
        this.mainImage = mainImage;
        this.subImages = subImages;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public Product() {
        super();
    }

    /**
     * 商品主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 商品主键id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 类目id
     *
     * @return
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 类目id
     *
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 商品名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 商品名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 商品副标题
     *
     * @return
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * 商品副标题
     *
     * @param subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    /**
     * 商品主图url(相对地址)
     *
     * @return
     */
    public String getMainImage() {
        return mainImage;
    }

    /**
     * 商品主图url(相对地址)
     *
     * @param mainImage
     */
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    /**
     * 商品附图(json格式,扩展用)
     *
     * @return
     */
    public String getSubImages() {
        return subImages;
    }

    /**
     * 商品附图(json格式,扩展用)
     *
     * @param subImages
     */
    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }

    /**
     * 商品详情
     *
     * @return
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 商品详情
     *
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * 商品单价(单位是元,保留两位小数)
     *
     * @return
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 商品单价(单位是元,保留两位小数)
     *
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 库存数量
     *
     * @return
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 库存数量
     *
     * @param stock
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 商品状态(1=在售,2=下架,3=删除)
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 商品状态(1=在售,2=下架,3=删除)
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * Product is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", subImages='" + subImages + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}