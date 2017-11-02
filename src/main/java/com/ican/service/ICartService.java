package com.ican.service;

import com.ican.common.ServiceResponse;

/**
 * 购物车服务逻辑
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 */
public interface ICartService {

    /**
     * 添加商品到购物车
     * @param userId 用户id
     * @param count 数量
     * @param productId 产品id
     * @return
     */
    ServiceResponse addProduct2Cart(Integer userId, Integer count, Integer productId);

    /**
     * 更新购物车中的数量
     * @param userId
     * @param count
     * @param productId
     * @return
     */
    ServiceResponse updateProduct2Cart(Integer userId,Integer count,Integer productId);

    /**
     * 删除购物车中的商品
     * @param userId
     * @param productIds
     * @return
     */
    ServiceResponse deleteProductFromCart(Integer userId,String productIds);

    /**
     * 用户查看购物车
     * @param userId
     * @return
     */
    ServiceResponse getProductFromCart(Integer userId);

    /**
     * 选中或者不选中
     * @param userId 用户id
     * @param checked 全选：1，全反选：0
     * @param productId 商品id
     * @return
     */
    ServiceResponse selectOrUnSelect(Integer userId,Integer checked,Integer productId);


    /**
     * 查询购物车里面商品的数量
     * @param userId
     * @return
     */
    ServiceResponse getCountFromCart(Integer userId);
}
