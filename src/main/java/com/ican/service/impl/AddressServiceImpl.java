package com.ican.service.impl;

import com.ican.common.ServiceResponse;
import com.ican.dao.ShippingMapper;
import com.ican.pojo.Shipping;
import com.ican.service.IAddressService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址服务逻辑
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/3 0003
 */
@Service("iAddressService")
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 新增收货地址
     * @param shipping 收货地址信息
     * @return 添加结果
     */
    public ServiceResponse addAddress(Shipping shipping){
        if (shipping!=null){
            int addCount=shippingMapper.insertSelective(shipping);
            if (addCount>0){
                return ServiceResponse.createBySuccess("添加成功");
            }else {
                return ServiceResponse.createByError("添加失败");
            }
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }

    /**
     * 删除地址
     * @param userId 用户id
     * @param addressId 地址
     * @return
     */
    public ServiceResponse deleteAddress(Integer userId,Integer addressId){
       if (userId==null||addressId==null){
           return ServiceResponse.createByError("传值为空，或者参数错误");
       }
        int deleteCount=shippingMapper.deleteAddressByUserIdAndAddressId(userId,addressId);
        if (deleteCount>0){
            return ServiceResponse.createBySuccess("删除成功");
        }else {
            return ServiceResponse.createByError("删除失败");
        }
    }

    /**
     * 更新地址
     * @param shipping
     * @return
     */
    public ServiceResponse updateAddress(Integer userId,Shipping shipping){
        if (shipping==null){
            return ServiceResponse.createByError("传值为空");
        }
        int updateCount=shippingMapper.updateAddressByShipping(shipping);
        if (updateCount>0){
            return ServiceResponse.createBySuccess("更新成功");
        }else {
            return ServiceResponse.createByError("更新失败");
        }
    }

    /**
     * 查询所有的地址
     * @param userId
     * @return
     */
    public ServiceResponse getAddress(Integer userId){
        if (userId==null){
            return ServiceResponse.createByError("传值为空");
        }
        List<Shipping> addressList=shippingMapper.selectAddressByUserId(userId);
        if (CollectionUtils.isNotEmpty(addressList)){
            return ServiceResponse.createBySuccess("收货地址",addressList);
        }else {
            return ServiceResponse.createByError("未添加收货地址");
        }
    }

    /**
     * 查询具体的地址
     * @param userId
     * @param addressId
     * @return
     */
    public ServiceResponse getDetailAddress(Integer userId,Integer addressId){
        if (userId==null||addressId==null){
            return ServiceResponse.createByError("传值为空");
        }
        Shipping shipping=shippingMapper.selectDetailAddressByUserIdAndAddressId(userId,addressId);
        if (shipping!=null){
            return ServiceResponse.createBySuccess("具体地址",shipping);
        }else {
            return ServiceResponse.createByError("查询失败");
        }
    }
}
