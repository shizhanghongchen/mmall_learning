package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 查询产品list页(后台)
     *
     * @return
     */
    List<Product> selectList();

    /**
     * 产品搜索list(后台)
     *
     * @param productName
     * @param productId
     * @return
     */
    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);

    /**
     * 产品列表 搜索 动态排序(前台)
     *
     * @param productName
     * @param categoryIdList
     * @return
     */
    List<Product> selectByNameAndCategoryIds(@Param("productName") String productName, @Param("categoryIdList") List<Integer> categoryIdList);

    /**
     * 根据产品id删除产品库存数量(返回值一定要使用Integer,因为int不能为NULL,考虑到很多商品有可能会被删除的情况)
     *
     * @param id
     * @return
     */
    Integer selectStockByProductId(Integer id);
}