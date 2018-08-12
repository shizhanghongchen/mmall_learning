package com.mmall.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.*;
import com.mmall.pojo.*;
import com.mmall.service.IOrderService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.FTPUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.OrderItemVo;
import com.mmall.vo.OrderProductVo;
import com.mmall.vo.OrderVo;
import com.mmall.vo.ShippingVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wb-yxk397023 on 2018/8/9.
 */
@Service("iOrderService")
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShippingMapper shippingMapper;


    /**
     * 创建订单
     *
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        // 从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
        // 获取购物车中订单列表
        ServerResponse serverResponse = getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        // 获取购物车中的订单列表数据
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        // 计算购物车总价
        BigDecimal payment = getOrderTotalPrice(orderItemList);
        // 获取生成的订单
        Order order = assembleOrder(userId, shippingId, payment);
        if (order == null) {
            // 如果生成失败直接返回
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            // 如果购物车信息为空直接返回
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        // 遍历订单列表信息
        for (OrderItem orderItem : orderItemList) {
            // 设置每个订单的订单号
            orderItem.setOrderNo(order.getOrderNo());
        }
        // mybatis 批量插入
        orderItemMapper.batchInsert(orderItemList);
        // 生成成功,减少产品的库存
        reduceProductStock(orderItemList);
        // 清空一下购物车
        cleanCart(cartList);
        // 组装OrderVo对象返回
        OrderVo orderVo = assembleOrderVo(order, orderItemList);
        // 返回
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 取消订单(未支付状态下取消订单)
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<String> cancel(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("该用户不存在此订单");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (row > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 获取购物车中的商品信息
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        // 创建购物车商品详情容器
        OrderProductVo orderProductVo = new OrderProductVo();
        // 从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
        // 获取购物车订单列表
        ServerResponse serverResponse = getCartOrderItem(userId, cartList);
        // 判断是否获取成功,如果不成功则直接返回
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        // 将获取到的购物车订单列表数据取出
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        // 创建订单商品信息容器
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        // 初始化总价
        BigDecimal payment = new BigDecimal("0");
        // 遍历获取到的购物车订单列表数据
        for (OrderItem orderItem : orderItemList) {
            // 设置总价
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            // 将信息添加到容器中
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        // 设置总价
        orderProductVo.setProductTotalPrice(payment);
        // 订单商品详情
        orderProductVo.setOrderItemVoList(orderItemVoList);
        // 设置主图
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        // 返回
        return ServerResponse.createBySuccess(orderProductVo);
    }

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("没有找到该订单");
    }

    /**
     * 用户个人中心查看订单列表
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = assembleOrderVoList(orderList, userId);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 获取订单list页(后台)
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVo> orderVoList = assembleOrderVoList(orderList, null);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 获取订单详情(后台)
     *
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    /**
     * 根据订单号进行搜索(后台)
     *
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = assembleOrderVo(order, orderItemList);

            PageInfo pageResult = new PageInfo(Lists.newArrayList(order));
            pageResult.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccess(pageResult);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    /**
     * 订单发货(后台)
     *
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccess("发货成功");
            }
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    /**
     * 订单支付
     *
     * @param orderNo
     * @param userId
     * @param path
     * @return
     */
    @Override
    public ServerResponse pay(Long orderNo, Integer userId, String path) {
        Map<String, String> resultMap = Maps.newHashMap();
        // 校验order是否存在
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("用户没有该订单!");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();
        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("mmall扫码支付,订单号:").append(outTradeNo).toString();
        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();
        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";
        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";
        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();
        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";
        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";
        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");
        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";
        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        for (OrderItem orderItem : orderItemList) {
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }
        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))
                .setGoodsDetailList(goodsDetailList);
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");
        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径(path需要手工加上/,否则将找不到指定的路径)
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
                File targetFile = new File(path, qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    log.error("上传二维码异常", e);
                }
                log.info("qrPath:" + qrPath);
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                resultMap.put("qrUrl", qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    /**
     * 支付宝回调
     *
     * @param params
     * @return
     */
    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        // 获取订单号
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        // 获取交易号
        String tradeNo = params.get("trade_no");
        // 获取交易状态
        String tradeStatus = params.get("trade_status");
        // 查询订单是否存在
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            // 如果为空则说明不是本商城订单
            return ServerResponse.createByErrorMessage("非本商城的订单,回调忽略");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            // 如果订单状态大于已付款则说明重复调用
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        // 如果状态一致则执行更新逻辑
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            // 设置订单支付时间
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            // 设置订单状态
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            // 更新订单信息
            orderMapper.updateByPrimaryKeySelective(order);
        }
        // 创建支付信息对象
        PayInfo payInfo = new PayInfo();
        // 设置用户id
        payInfo.setUserId(order.getUserId());
        // 设置订单号
        payInfo.setOrderNo(order.getOrderNo());
        // 设置支付类型
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        // 设置交易号
        payInfo.setPlatformNumber(tradeNo);
        // 设置支付状态
        payInfo.setPlatformStatus(tradeStatus);
        // 插入数据库
        payInfoMapper.insert(payInfo);
        // 返回
        return ServerResponse.createBySuccess();
    }

    /**
     * 查询订单支付状态
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 简单打印应答
     *
     * @param response
     */
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    /**
     * 获取购物车订单列表
     *
     * @param userId
     * @param cartList
     * @return
     */
    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
        // 创建容器
        List<OrderItem> orderItemList = Lists.newArrayList();
        // 判断购物车是否为空
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        // 校验购物车的数据,包括产品的状态和数量
        for (Cart cartItem : cartList) {
            // 创建产品容器
            OrderItem orderItem = new OrderItem();
            // 通过商品id查询商品详情
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            // 判断商品是否是在售状态
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                // 如果不是在售状态直接返回
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "不是在线售卖状态");
            }
            // 校验库存
            if (cartItem.getQuantity() > product.getStock()) {
                // 如果购物车商品数量大于数据库数量则直接返回
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "库存不足");
            }
            // 设置用户id
            orderItem.setUserId(userId);
            // 设置商品id
            orderItem.setProductId(product.getId());
            // 设置产品名称
            orderItem.setProductName(product.getName());
            // 设置商品主图
            orderItem.setProductImage(product.getMainImage());
            // 设置商品价格(价格为添加购物车时的价格)
            orderItem.setCurrentUnitPrice(product.getPrice());
            // 设置商品数量
            orderItem.setQuantity(cartItem.getQuantity());
            // 设置商品总价
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));
            // 将设置好的商品添加到容器内
            orderItemList.add(orderItem);
        }
        // 返回结果
        return ServerResponse.createBySuccess(orderItemList);
    }

    /**
     * 计算订单总价
     *
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        // 使用BigDecimal String构造器初始化
        BigDecimal payment = new BigDecimal("0");
        // 遍历订单列表
        for (OrderItem orderItem : orderItemList) {
            // 将每一个订单的总价相加获取总价
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        // 返回总价
        return payment;
    }

    /**
     * 生成订单(组装订单)
     *
     * @param userId
     * @param shippingId
     * @param payment
     * @return
     */
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        // 创建订单容器
        Order order = new Order();
        // 获取订单号
        long orderNo = generateOrderNo();
        // 设置订单号
        order.setOrderNo(orderNo);
        // 设置订单状态
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        // 设置运费
        order.setPostage(0);
        // 设置订单支付类型
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        // 设置订单实付金额
        order.setPayment(payment);
        // 设置用户id
        order.setUserId(userId);
        order.setShippingId(shippingId);
        //发货时间等等
        //付款时间等等
        // 保存订单信息
        int rowCount = orderMapper.insert(order);
        if (rowCount > 0) {
            // 如果保存成功返回订单
            return order;
        }
        return null;
    }

    /**
     * 生成订单号
     *
     * @return
     */
    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    /**
     * 更新产品库存
     *
     * @param orderItemList
     */
    private void reduceProductStock(List<OrderItem> orderItemList) {
        // 遍历订单列表
        for (OrderItem orderItem : orderItemList) {
            // 根据单个订单的商品id查询出商品信息
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            // 设置库存(数据库库存减去订单商品数量)
            product.setStock(product.getStock() - orderItem.getQuantity());
            // 将新的商品信息更新回数据库
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * 清空购物车
     *
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList) {
        // 遍历购物车信息
        for (Cart cart : cartList) {
            // 将单个购物车信息删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 组装OrderVo对象返回
     *
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        // 创建容器
        OrderVo orderVo = new OrderVo();
        // 设置订单号
        orderVo.setOrderNo(order.getOrderNo());
        // 设置总价
        orderVo.setPayment(order.getPayment());
        // 设置支付类型
        orderVo.setPaymentType(order.getPaymentType());
        // 设置支付类型描述
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        // 设置邮费
        orderVo.setPostage(order.getPostage());
        // 设置状态
        orderVo.setStatus(order.getStatus());
        // 设置状态描述
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());
        // 设置收货信息id
        orderVo.setShippingId(order.getShippingId());
        // 根据收货地址id获取收货地址详情
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            // 如果收货地址详情不为空则设置收货人姓名(用于购物车展示)
            orderVo.setReceiverName(shipping.getReceiverName());
            // 设置收货信息详情
            orderVo.setShippingVo(assembleShippingVo(shipping));
        }
        // 设置支付时间
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        // 设置发货时间
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        // 设置交易结束时间
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        // 设置创建时间
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        // 设置订单关闭时间
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        // 设置主图
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        // 创建商品详情集合
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        // 遍历购物车中的订单列表数据
        for (OrderItem orderItem : orderItemList) {
            // 拿到单个订单商品详情
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            // 将单个商品详情添加到商品详情集合中
            orderItemVoList.add(orderItemVo);
        }
        // 设置订单明细
        orderVo.setOrderItemVoList(orderItemVoList);
        // 返回
        return orderVo;
    }

    /**
     * 设置收货信息详情
     *
     * @param shipping
     * @return
     */
    private ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
        return shippingVo;
    }

    /**
     * 设置单个订单详情
     *
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    /**
     * 组装订单列表
     *
     * @param orderList
     * @param userId
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList();
            if (userId == null) {
                // 管理员查询的时候不需要传userId
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }
}
