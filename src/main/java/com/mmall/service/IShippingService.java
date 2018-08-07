package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by wb-yxk397023 on 2018/8/7.
 */
public interface IShippingService {

    /**
     * 新增收货地址
     *
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 删除收货地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<String> del(Integer userId, Integer shippingId);

    /**
     * 更新收货地址
     *
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse update(Integer userId, Shipping shipping);

    /**
     * 查询单个收货地址信息
     *
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    /**
     * 根据userId查询全部收货地址信息
     *
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> list(int pageNum, int pageSize, Integer userId);
}
