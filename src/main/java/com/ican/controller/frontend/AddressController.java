package com.ican.controller.frontend;

import com.ican.common.ServiceResponse;
import com.ican.common.TokenCache;
import com.ican.pojo.Shipping;
import com.ican.pojo.User;
import com.ican.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 问题是：如何判断是修改的自己的东西 ，使用每次请求携带一个token验证
 * 地址控制器
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/3 0003
 */
@Controller
@RequestMapping("address")
public class AddressController {
    @Autowired
    private IAddressService iAddressService;

    /**
     * 新增收货地址
     * @param shipping 收货地址信息
     * @return 添加结果
     */
    @RequestMapping(value = "add_address.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse addAddress(String appToken,Shipping shipping){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==shipping.getUserId()){
            return iAddressService.addAddress(shipping);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    /**
     * 删除地址
     * @param userId
     * @param addressId
     * @return
     */
    @RequestMapping(value = "delete_address.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse deleteAddress(String appToken,Integer userId,Integer addressId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iAddressService.deleteAddress(userId,addressId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }

    /**
     * 更新地址
     * @param shipping
     * @return
     */
    @RequestMapping(value = "update_address.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse updateAddress(String appToken,Integer userId,Shipping shipping){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iAddressService.updateAddress(userId,shipping);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    /**
     * 查询具体的地址
     * @param userId
     * @param addressId
     * @return
     */
    @RequestMapping(value = "get_detail_address.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServiceResponse getDetailAddress(String appToken,Integer userId,Integer addressId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iAddressService.getDetailAddress(userId,addressId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }
    /**
     * 查询所有的地址
     * @param userId
     * @return
     */
    @RequestMapping(value = "get_all_address.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getAllAddress(String appToken,Integer userId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iAddressService.getAddress(userId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }
}
