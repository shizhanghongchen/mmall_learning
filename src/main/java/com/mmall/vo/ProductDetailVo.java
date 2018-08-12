package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by wb-yxk397023 on 2018/8/4.
 */
@Getter
@Setter
public class ProductDetailVo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 品类id
     */
    private Integer categoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 主图
     */
    private String mainImage;

    /**
     * 附图
     */
    private String subImages;

    /**
     * 详情
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
     * 销售状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 图片服务器地址
     */
    private String imageHost;

    /**
     * 父类目id
     */
    private Integer parentCategoryId;
}
