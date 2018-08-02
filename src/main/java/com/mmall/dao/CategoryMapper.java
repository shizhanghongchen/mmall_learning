package com.mmall.dao;

import com.mmall.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     *
     * @param parentId
     * @return
     */
    List<Category> selectCategoryChildrenByParentId(Integer parentId);
}