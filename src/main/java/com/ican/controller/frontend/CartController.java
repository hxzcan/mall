package com.ican.controller.frontend;

import com.ican.common.Constract;
import com.ican.common.ServiceResponse;
import com.ican.common.TokenCache;
import com.ican.pojo.User;
import com.ican.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 购物车接口
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 */
@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    private ICartService iCartService;

    /**
     * 添加商品到购物车  这是商品详情页面添加
     * @param userId 用户id
     * @param count 数量
     * @param productId 商品id
     * @return 添加结果
     */
    @RequestMapping(value = "add_product2Cart.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse addProduct2Cart(String appToken,Integer userId,Integer count,Integer productId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.addProduct2Cart(userId,count,productId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }

    /**
     * 更新购物车的数量 这是在购物车里面修改数量
     * @param userId
     * @param count
     * @param productId
     * @return 更新结果
     */
    @RequestMapping(value = "update_product2Cart.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse updateProduct2Cart(String appToken,Integer userId,Integer count,Integer productId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.updateProduct2Cart(userId,count,productId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }

    /**
     * 删除购物车中的
     * @param userId
     * @param productIds 多个商品的id 每个之间以，分隔
     * @return 删除结果
     */
    @RequestMapping(value = "delete_productFromCart.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse deleteProductFromCart(String appToken,Integer userId,String productIds){

        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.deleteProductFromCart(userId,productIds);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    /**
     * 用户查看购物车中的所有商品
     * @param userId
     * @return 所有商品信息
     */
    @RequestMapping(value = "get_productFromCart.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getProductFromCart(String appToken,Integer userId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.getProductFromCart(userId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    //全选 全反选
    /**
     * 用户全选购物车中的商品
     * @param userId
     * @ checked 选择：1 ，未选择：0
     * @return 所有商品信息
     */
    @RequestMapping(value = "select_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse selectAll(String appToken,Integer userId){

        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.selectOrUnSelect(userId, Constract.CartProductStatus.CHECKED,null);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    /**
     * 全不选
     * @param userId
     * @ checked 选择：1 ，未选择：0
     * @return 所有商品信息
     */
    @RequestMapping(value = "unSelect_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse unSelectAll(String appToken,Integer userId){

        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.selectOrUnSelect(userId, Constract.CartProductStatus.UNCHECKED,null);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    //单独选 单独反选
    /**
     * 单个选中
     * @param userId
     * @checked 选择：1 ，未选择：0
     * @param productId 商品id
     * @return 所有商品信息
     */
    @RequestMapping(value = "select_single.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse selectSingle(String appToken,Integer userId,Integer productId){

        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.selectOrUnSelect(userId,Constract.CartProductStatus.CHECKED,productId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    /**
     * 单个不选中
     * @param userId
     * @checked 选择：1 ，未选择：0
     * @param productId 商品id
     * @return 所有商品信息
     */
    @RequestMapping(value = "unSelect_single.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse unSelectSingle(String appToken,Integer userId,Integer productId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.selectOrUnSelect(userId,Constract.CartProductStatus.UNCHECKED,productId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }

    }

    //查询当前购物车里面的产品数量
    /**
     * 查询购物车里面商品的数量
     * @param userId
     * @return
     */
    @RequestMapping(value = "getCount_FromCart.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getCountFromCart(String appToken,Integer userId){
        User user= (User) TokenCache.getValue(appToken);
        if (user==null){
            return ServiceResponse.createByError("用户未登录");
        }
        if (user.getId()==userId){
            return iCartService.getCountFromCart(userId);
        }else {
            return ServiceResponse.createByError("参数错误");
        }
    }

}
