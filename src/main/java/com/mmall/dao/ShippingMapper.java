package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    /**
     * 根据userId及shippingId删除收货地址(防止横向越权)
     *
     * @param userId
     * @param shippingId
     * @return
     */
    int deleteByShippingIdUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 根据userId及shippingId更新收货地址(防止横向越权)
     *
     * @param shipping
     * @return
     */
    int updateByShipping(Shipping shipping);

    /**
     * 根据userId及shippingId查询单个收货地址(防止横向越权)
     *
     * @param userId
     * @param shippingId
     * @return
     */
    Shipping selectByShippingIdUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 根据userId查询全部收货地址集合
     *
     * @param userId
     * @return
     */
    List<Shipping> selectByUserId(@Param("userId")Integer userId);
}