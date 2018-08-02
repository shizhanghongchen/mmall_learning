package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by wb-yxk397023 on 2018/8/2.
 */
public interface ICategoryService {

    /**
     * 添加类目
     *
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新类目名称
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse setCategoryName(Integer categoryId, String categoryName);

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     *
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查询本节点的id及孩子节点的id
     *
     * @param categoryId
     * @return
     */
    ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategory(Integer categoryId);
}
