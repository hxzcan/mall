package com.ican.controller.frontend;

import com.github.pagehelper.PageInfo;
import com.ican.common.ServiceResponse;
import com.ican.service.IProductService;
import com.ican.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;


/**
 * 前端展示的
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/31 0031
 */
@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    /**
     * 商品的具体内容
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getProduct_details.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse<ProductDetailVo> getProductDetails(Integer productId){

        return iProductService.frontGetProductDetails(productId);
    }

    /**
     * 搜索商品列表
     * @param keyWord 关键字 required = false表示不是必须传的
     * @param categoryId 分类id
     * @param pageNumber
     * @param pageSize
     * @param orderBy 排序类型
     * @return
     */
    @RequestMapping(value = "/getProduct_list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse<PageInfo> productList(@RequestParam(value = "keyWord",required = false) String keyWord,
                                                 @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                 @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        String newKeyword=null;
        try {
             newKeyword=new String(keyWord.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return iProductService.frontGetProductByKeyWordAndCategoryId(newKeyword,categoryId,pageNumber,pageSize,orderBy);
    }

    /**
     * 获取统一分类的下的所有产品
     * @param categoryId 分类的id
     * @return
     */
    @RequestMapping(value = "/getParallelProduct_list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getParallelProduct(Integer categoryId,
                                              @RequestParam(value = "pageNumber" ,defaultValue = "1") Integer pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return iProductService.getParallelProduct(categoryId,pageNumber,pageSize);
    }
}
