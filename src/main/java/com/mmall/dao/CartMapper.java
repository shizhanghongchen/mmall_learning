package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    /**
     * 查询该用户购物车中是否有此商品
     *
     * @param userId
     * @param productId
     * @return
     */
    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 根据用户id查询出购物车集合
     *
     * @param userId
     * @return
     */
    List<Cart> selectCartByUserId(Integer userId);

    /**
     * 查询用户购物车中商品是否全选
     *
     * @param userId
     * @return
     */
    int selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 根据用户id及商品id从购物车删除商品
     *
     * @param userId
     * @param productIds
     * @return
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIds") List<String> productIds);

    /**
     * 全选/全反选(根据checked参数),单选/单反选(根据productId)
     *
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    /**
     * 查询当前用户的购物车里面的产品数量(如果一个产品有10个,那么数量就是10)
     *
     * @param userId
     * @return
     */
    int getCartProductCount(Integer userId);
}