package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wb-yxk397023 on 2018/8/7.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 新增收货地址
     *
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int insert = shippingMapper.insert(shipping);
        if (insert > 0) {
            Map map = Maps.newHashMap();
            map.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功!", map);
        }
        return ServerResponse.createByErrorMessage("新建地址失败!");
    }

    /**
     * 删除收货地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId) {
        int deleteByShippingIdUserId = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (deleteByShippingIdUserId > 0) {
            return ServerResponse.createBySuccessMessage("删除地址成功!");
        }
        return ServerResponse.createByErrorMessage("删除地址失败!");
    }

    /**
     * 更新收货地址
     *
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int updateByShipping = shippingMapper.updateByShipping(shipping);
        if (updateByShipping > 0) {
            return ServerResponse.createBySuccessMessage("更新收货地址成功!");
        }
        return ServerResponse.createByErrorMessage("更新收货地址失败!");
    }

    /**
     * 查询单个收货地址信息
     *
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.createByErrorMessage("无法查询到该地址!");
        }
        return ServerResponse.createBySuccess("查询地址成功!", shipping);
    }

    /**
     * 根据userId查询全部收货地址信息
     *
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<PageInfo> list(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippings = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippings);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
