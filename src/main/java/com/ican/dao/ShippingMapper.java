package com.ican.dao;

import com.ican.pojo.Shipping;
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
     * 查询用户所有的收货地址
     * @param userId
     * @return
     */
    List<Shipping> selectAddressByUserId(Integer userId);

    /**
     * 查询具体的地址
     * @param userId
     * @param addressId
     * @return
     */
    Shipping selectDetailAddressByUserIdAndAddressId(@Param("userId") Integer userId,@Param("addressId") Integer addressId);
    /**
     * 根据用户id和地址id删除
     * @param userId
     * @param addressId
     * @return
     */
    int deleteAddressByUserIdAndAddressId(@Param("userId") Integer userId,@Param("addressId") Integer addressId);

    /**
     * 更新
     * @param shipping
     * @return
     */
    int updateAddressByShipping(Shipping shipping);
}