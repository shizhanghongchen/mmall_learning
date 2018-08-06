package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by wb-yxk397023 on 2018/8/6.
 */
public interface ICartService {

    /**
     * 加入购物车
     *
     * @param userId
     * @param count
     * @param productId
     * @return
     */
    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);

    /**
     * 更新购物车
     *
     * @param userId
     * @param count
     * @param productId
     * @return
     */
    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    /**
     * 从购物车中删除商品
     *
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    /**
     * 查询购物车list页
     *
     * @param userId
     * @return
     */
    ServerResponse<CartVo> list(Integer userId);

    /**
     * 全选/全反选(根据checked参数),单选/单反选(根据productId)
     *
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    /**
     * 查询当前用户的购物车里面的产品数量(如果一个产品有10个,那么数量就是10)
     *
     * @param userId
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
