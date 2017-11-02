package com.ican.dao;

import com.ican.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    /**
     * 查询购物车的产品
     * @param userId 用户id
     * @param productId 商品id
     * @return
     */
    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);

    /**
     * 根据用户id查询这个用户添加的所有商品
     * @param userId 用户id
     * @return
     */
    List<Cart> selectCartByUserId(Integer userId);

    /**
     * 查询购物车选中的状态数量
     * @param userId
     * @return
     */
    int selectProductCheckedStatusByUserId(Integer userId);

    /**
     * 删除购物车中的商品
     * @param userId
     * @param productIds
     * @return
     */
    int deleteCartByUserIdAndProductIds(@Param("userId") Integer userId,
                                        @Param("productIds") List<String> productIds);

    /**
     * 全选或者全反选
     * @param userId
     * @param checked 选择1，不选：0
     * @return
     */
    int checkedOrUnCheckedProduct(@Param("userId") Integer userId,@Param("checked") Integer checked,
                                  @Param("productId") Integer productId);

    /**
     * 查询具体用户购物车中商品的数量
     * @param userId
     * @return
     */
    int selectProductCount(Integer userId);
}