package com.ican.service;

import com.ican.common.ServiceResponse;
import com.ican.pojo.Product;

/**
 * 产品服务逻辑接口
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/27 0027
 * Time: 16:31
 */
public interface IProductService {

    /**
     * 添加和更新产品
     * @param product 添加的产品
     * @return 添加和更新结果
     */
    ServiceResponse<String> addOrUpdateProduct(Product product);

    /**
     * 更新产品的状态
     * @param productId 产品的id
     * @param productStatus 产品的状态
     * @return 更新结果
     */
    ServiceResponse<String> updateProductStatus(Integer productId,Integer productStatus);

    /**
     * 查询产品的具体内容
     * @param productId 产品id
     * @return 产品
     */
    ServiceResponse<Object> getProductDetails(Integer productId);

    /**
     * 分页显示商品列表
     * @param pageNumber 第几页数
     * @param pageSize 每页的数量
     * @return 列表
     */
    ServiceResponse getProductList(Integer pageNumber,Integer pageSize);

    /**
     * 根据名字或者id查询
     * @param productName 商品名
     * @param productId 商品id
     * @param pageNumber 第几页
     * @param pageSiz 每页的数量
     * @return 查询结果
     */
    ServiceResponse productSearch(String productName,Integer productId,Integer pageNumber,Integer pageSiz);
}
