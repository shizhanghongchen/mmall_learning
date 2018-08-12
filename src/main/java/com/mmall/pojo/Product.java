package com.mmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}