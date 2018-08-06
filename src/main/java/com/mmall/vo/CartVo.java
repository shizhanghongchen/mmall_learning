package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wb-yxk397023 on 2018/8/6.
 */
public class CartVo {

    /**
     * 私有对象
     */
    private List<CartProductVo> cartProductVoList;

    /**
     * 购物车总价
     */
    private BigDecimal cartTotalPrice;

    /**
     * 是否全选
     */
    private Boolean allChecked;

    /**
     * 图片地址
     */
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    @Override
    public String toString() {
        return "CartVo{" +
                "cartProductVoList=" + cartProductVoList +
                ", cartTotalPrice=" + cartTotalPrice +
                ", allChecked=" + allChecked +
                ", imageHost='" + imageHost + '\'' +
                '}';
    }
}
