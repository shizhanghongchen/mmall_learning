package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wb-yxk397023
 * @date 2018/8/6
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {


    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 加入购物车
     *
     * @param userId
     * @param count
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            // 如果为空,则说明此商品不在购物车中需要新增
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        } else {
            // 如果不为空,则说明购物车中有此商品需要执行更新操作
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        return list(userId);
    }

    /**
     * 更新购物车
     *
     * @param userId
     * @param count
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return list(userId);
    }

    /**
     * 从购物车中删除商品
     *
     * @param userId
     * @param productIds
     * @return
     */
    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        // Guava Splitter进行字符串拆分
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productList);
        return list(userId);
    }

    /**
     * 查询购物车list页
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 全选/全反选(根据checked参数),单选/单反选(根据productId)
     *
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        cartMapper.checkedOrUncheckedProduct(userId, productId, checked);
        return list(userId);
    }

    /**
     * 查询当前用户的购物车里面的产品数量(如果一个产品有10个,那么数量就是10)
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if (userId == null) {
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.getCartProductCount(userId));
    }

    /**
     * 购物车核心方法
     *
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId) {
        // 创建购物车容器
        CartVo cartVo = new CartVo();
        // 根据用户id查询出购物车集合
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        // 创建购物车商品容器集合
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        // 初始购物车总价
        BigDecimal cartTotalPrice = new BigDecimal("0");
        // 判断购物车集合是否为空
        if (CollectionUtils.isNotEmpty(cartList)) {
            // 如果不为空则遍历购物车集合
            for (Cart cartItem : cartList) {
                // 创建购物车商品容器
                CartProductVo cartProductVo = new CartProductVo();
                // 设置id
                cartProductVo.setId(cartItem.getId());
                // 设置用户id
                cartProductVo.setUserId(cartItem.getUserId());
                // 设置商品id
                cartProductVo.setProductId(cartItem.getProductId());
                // 查询商品信息
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                // 判断商品信息是否为空,如果不为空则开始设置购物车商品信息
                if (product != null) {
                    // 商品主图
                    cartProductVo.setProductMainImage(product.getMainImage());
                    // 商品名称
                    cartProductVo.setProductName(product.getName());
                    // 商品副标题
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    // 商品状态
                    cartProductVo.setProductStatus(product.getStatus());
                    // 商品价格
                    cartProductVo.setProductPrice(product.getPrice());
                    // 商品库存
                    cartProductVo.setProductStock(product.getStock());
                    // 设置库存初始容量
                    int buyLimitCount = 0;
                    // 判断商品库存是否充足
                    if (product.getStock() >= cartItem.getQuantity()) {
                        // 如果充足将库存初始容量赋值为购物车库存数量
                        buyLimitCount = cartItem.getQuantity();
                        // 设置超售标识位
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        // 如果库存不足将商品信息中的最大库存数量赋值给库存初始容量
                        buyLimitCount = product.getStock();
                        // 设置超售标识位
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        // 创建新的购物车容器
                        Cart cartForQuantity = new Cart();
                        // 设置购物车id
                        cartForQuantity.setId(cartItem.getId());
                        // 设置最大库存
                        cartForQuantity.setQuantity(buyLimitCount);
                        // 购物车中更新有效库存
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    // 设置购物车中此商品的数量
                    cartProductVo.setQuantity(buyLimitCount);
                    // 计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    // 设置选中状态
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                // 判断选中状态
                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    // 如果是已选中状态则将价格增加到购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                // 将单个购物车对象添加到购物车对象集合中
                cartProductVoList.add(cartProductVo);
            }
        }
        // 将购物车对象集合放在购物车容器中
        cartVo.setCartProductVoList(cartProductVoList);
        // 设置购物车总价
        cartVo.setCartTotalPrice(cartTotalPrice);
        // 设置主图地址
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        // 设置全选属性
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        return cartVo;
    }

    /**
     * 判断是否全选
     *
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
