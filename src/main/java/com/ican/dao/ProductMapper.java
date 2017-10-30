package com.ican.dao;

import com.ican.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 获取产品列表
     * @return 所有的商品
     */
    List<Product> getProductList();

    /**
     * 根据名字查询产品
     * @param productName 产品名
     * @param productId 产品id
     * @return 产品列表
     */
    List<Product> selectProductByNameAndId(@Param("productName") String productName,@Param("productId") Integer productId);

}