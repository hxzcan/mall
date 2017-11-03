package com.ican.service;

import com.ican.common.ServiceResponse;
import com.ican.pojo.Shipping;

/**
 *收货地址服务接口
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/3 0003
 */
public interface IAddressService {
    /**
     * 新增收货地址
     * @param shipping 收货地址信息
     * @return 添加结果
     */
     ServiceResponse addAddress(Shipping shipping);

    /**
     * 删除地址
     * @param userId 用户id
     * @param addressId 地址
     * @return
     */
    ServiceResponse deleteAddress(Integer userId,Integer addressId);


    /**
     * 更新地址
     * @param userId
     * @param shipping
     * @return
     */
    ServiceResponse updateAddress(Integer userId,Shipping shipping);

    /**
     * 查询所有的地址
     * @param userId
     * @return
     */
    ServiceResponse getAddress(Integer userId);

    /**
     * 查询具体的地址
     * @param userId
     * @param addressId
     * @return
     */
     ServiceResponse getDetailAddress(Integer userId,Integer addressId);
}
