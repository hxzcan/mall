package com.ican.controller.backend;

import com.ican.common.Constract;
import com.ican.common.ResponseCode;
import com.ican.common.ServiceResponse;
import com.ican.pojo.Product;
import com.ican.pojo.User;
import com.ican.service.IProductService;
import com.ican.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 *
 * 产品的管理
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/27 0027
 * Time: 16:25
 */
@Controller
@RequestMapping("manager/product")
public class ProductManagerController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    /**
     * 添加和更新产品
     * @param session session值
     * @param product 产品
     * @return 添加和更新结果
     */
    @RequestMapping(value = "save_product.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse saveProduct(HttpSession session, Product product){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            //是管理员，添加产品
            return iProductService.addOrUpdateProduct(product);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 更新产品状态
     * @param session
     * @param productId 产品
     * @param productStatus 产品状态
     * @return 更新结果
     */
    @RequestMapping(value = "set_product_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse setProductStatus(HttpSession session, Integer productId,Integer productStatus){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.updateProductStatus(productId,productStatus);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 查询产品的具体内容
     * @param productId 产品id
     * @return 产品
     */
    @RequestMapping(value = "get_product_details.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getProductDetails(HttpSession session, Integer productId){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.getProductDetails(productId);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 商品列表展示，分页展示
     * @param session
     * @param pageNumber 第几页数 默认值是1
     * @param pageSize 每页显示的数目 默认10条
     * @return 列表
     */
    @RequestMapping(value = "get_product_list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getProductList(HttpSession session,
                                          @RequestParam(value="pageNumber",defaultValue = "1") Integer pageNumber,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.getProductList(pageNumber,pageSize);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 搜索产品
     * @param session
     * @param productName 产品名字
     * @param productId 产品id
     * @param pageNumber 第几页 默认的是第一页
     * @param pageSize 一页显示的数量 默认的是每页10条
     * @return 搜索结果
     */
    @RequestMapping(value = "product_search.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse productSearch(HttpSession session,String productName,Integer productId,
                                         @RequestParam(value="pageNumber",defaultValue = "1") Integer pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.productSearch(productName,productId,pageNumber,pageSize);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

}

