package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IOrderService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wb-yxk397023 on 2018/8/12.
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IOrderService iOrderService;

    /**
     * 获取订单list页(后台)
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iOrderService.manageList(pageNum, pageSize);
    }

    /**
     * 获取订单详情(后台)
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(Long orderNo) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iOrderService.manageDetail(orderNo);
    }

    /**
     * 根据订单号进行搜索(后台)
     *
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }

    /**
     * 订单发货(后台)
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(Long orderNo) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iOrderService.manageSendGoods(orderNo);
    }
}
