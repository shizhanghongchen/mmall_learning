package com.mmall.pojo;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
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

    /**
     * 全参构造器
     *
     * @param id         类目主键id
     * @param parentId   父类目id(当parentId=0时说明是根类目,一级类目)
     * @param name       类目名称
     * @param status     类目状态(1=正常,2=已废弃)
     * @param sortOrder  排序编号(同类展示顺序,数值相等则自然排序)
     * @param createTime 创建时间
     * @param updateTime 更新时间
     */
    public Category(Integer id, Integer parentId, String name, Boolean status, Integer sortOrder, Date createTime, Date updateTime) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.status = status;
        this.sortOrder = sortOrder;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public Category() {
        super();
    }

    /**
     * 类目主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 类目主键id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 父类目id(当parentId=0时说明是根类目,一级类目)
     *
     * @return
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父类目id(当parentId=0时说明是根类目,一级类目)
     *
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 类目名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 类目名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 类目状态(1=正常,2=已废弃)
     *
     * @return
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 类目状态(1=正常,2=已废弃)
     *
     * @param status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 排序值(同类展示顺序,如果数值相等则自然排序)
     *
     * @return
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 排序值(同类展示顺序,如果数值相等则自然排序)
     *
     * @param sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
     * equals重写
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return id != null ? id.equals(category.id) : category.id == null;
    }

    /**
     * hashCode重写
     *
     * @return
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Category is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", sortOrder=" + sortOrder +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}