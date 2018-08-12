package com.mmall.pojo;

import lombok.*;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    /**
     * 类目主键id
     */
    private Integer id;

    /**
     * 父类目id(当parentId=0时说明是根节点,一级类目)
     */
    private Integer parentId;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 类目状态(1=正常,2=已废弃)
     */
    private Boolean status;

    /**
     * 排序编号(同类展示顺序,数值相等则自然排序)
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}