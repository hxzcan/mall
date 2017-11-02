package com.ican.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 总的购物车
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 */
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allCheck;//是否全勾选
    private String imageHost;//访问图片的地址

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

    public Boolean getAllCheck() {
        return allCheck;
    }

    public void setAllCheck(Boolean allCheck) {
        this.allCheck = allCheck;
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
                ", allCheck=" + allCheck +
                ", imageHost='" + imageHost + '\'' +
                '}';
    }
}
