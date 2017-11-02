package com.ican.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ican.common.Constract;
import com.ican.common.ServiceResponse;
import com.ican.dao.CategoryMapper;
import com.ican.dao.ProductMapper;
import com.ican.pojo.Category;
import com.ican.pojo.Product;
import com.ican.service.ICategoryService;
import com.ican.service.IProductService;
import com.ican.utils.DateTimeUtil;
import com.ican.utils.PropertisUtil;
import com.ican.vo.ProductDetailVo;
import com.ican.vo.ProductListVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品服务逻辑处理层
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/27 0027
 * Time: 16:32
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;



    /**
     * 添加和更新产品
     * @param product 添加的产品
     * @return 添加和更新结果
     */
    public ServiceResponse<String> addOrUpdateProduct(Product product){
        if (product!=null){
            //这是产品的子图，有多张，取其中的一张作为主图
            if (StringUtils.isNoneBlank(product.getSubImages())){
                String[] subImageArray=product.getSubImages().split(";");
                if (subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            //如果是更新，产品的id不会是空的
          if (product.getId()!=null){
            int updateCount=productMapper.updateByPrimaryKeySelective(product);
              if (updateCount>0){
                  return ServiceResponse.createBySuccess("更新成功");
              }else {
                  return ServiceResponse.createByError("更新失败");
              }
          }else {
              //添加产品
              product.setStatus(1);
              int addCount=productMapper.insert(product);
              if (addCount>0){
                  return ServiceResponse.createBySuccess("添加成功");
              }else {
                  return ServiceResponse.createByError("添加失败");
              }
          }
        }else {
            return ServiceResponse.createIllegal("参数错误");
        }
    }

    /**
     * 更新产品的状态
     * @param productId 产品的id
     * @param productStatus 产品的状态
     * @return 更新结果
     */
    public ServiceResponse<String> updateProductStatus(Integer productId,Integer productStatus){
        if (productId!=null){
            if (productStatus==Constract.ProductStatus.PRODUCT_ONLINE||productStatus==Constract.ProductStatus.PRODUCT_DELETE
                    ||productStatus==Constract.ProductStatus.PRODUCT_OFF_THE_SHELF){
                Product product=new Product();
                product.setId(productId);
                product.setStatus(productStatus);
                int updateCount=productMapper.updateByPrimaryKeySelective(product);
                if (updateCount>0){
                    return  ServiceResponse.createBySuccess("更新成功");
                }else {
                    return ServiceResponse.createByError("更新失败");
                }
            }else {
                return ServiceResponse.createByError("传的参数错误，不符合规定");
            }
        }else {
            return ServiceResponse.createByError("参数错误，没有传参");
        }
    }

    /**
     * 查询某个产品的具体内容
     * @param productId 产品id
     * @return 产品
     */
    public ServiceResponse<Object> getProductDetails(Integer productId){
        if (productId!=null){
            Product product=productMapper.selectByPrimaryKey(productId);
            if (product!=null){
                //简单的vo对象 values-object
                //复杂的bo pojo->bo(business object)-vo(view object)
                //现在使用vo
                ProductDetailVo productDetailVo=assembleProductDetailVo(product);

                return ServiceResponse.createBySuccess("请求成功",productDetailVo);
            }else {
                return ServiceResponse.createByError("没有这个产品");
            }
        }else {
            return ServiceResponse.createByError("参数错误,没有传参");
        }
    }

    /**
     * 分页显示商品列表
     * @param pageNumber 第几页数
     * @param pageSize 每页的数量
     * @return 列表
     */
    public ServiceResponse getProductList(Integer pageNumber,Integer pageSize){
        //1.配置pageHelper开始的位置 startPage
        PageHelper.startPage(pageNumber,pageSize);
        //2.填充sql查询逻辑
        List<Product> productList=productMapper.getProductList();
        List<ProductListVo> productListVoList= Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(productList)){
            for (Product product:productList) {
                ProductListVo productListVo=assembleProductListVo(product);
                productListVoList.add(productListVo);
            }
        }
        //3.配置pageHelper的收尾
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(productListVoList);

        return ServiceResponse.createBySuccess("请求成功",pageResult);
    }

    /**
     * 根据名字或者id查询 分页查询
     * @param productName 商品名
     * @param productId 商品id
     * @param pageNumber 第几页
     * @param pageSiz 每页的数量
     * @return 查询结果
     */
    public ServiceResponse productSearch(String productName,Integer productId,Integer pageNumber,Integer pageSiz){

        PageHelper.startPage(pageNumber,pageSiz);

        if (StringUtils.isNoneBlank(productName)){
            productName=new StringBuilder().append("%").append(productName).append("%").toString();
        }

        List<Product> productList=productMapper.selectProductByNameAndId(productName,productId);
        List<ProductListVo> productListVoList=Lists.newArrayList();
        for (Product product:productList) {
            ProductListVo productListVo=assembleProductListVo(product);
            productListVoList.add(productListVo);
        }

        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServiceResponse.createBySuccess("请求成功",pageResult);
    }


    //ProductDetailVo数据的进一步封装
    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo=new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setName(product.getName());
        productDetailVo.setCategoryotId(product.getCategoryotId());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost(PropertisUtil.getStringProperty("product.imageHost","http://192.168.201.131:8080/webApp/"));
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryotId());
        if (category==null){
            productDetailVo.setParentCategoryId(0);
        }else {
            Integer parentCategoryId=category.getParentId();
            productDetailVo.setParentCategoryId(parentCategoryId);
        }

        productDetailVo.setCreatDate(DateTimeUtil.date2String(product.getCreateTime(),null));
        productDetailVo.setUpdateDate(DateTimeUtil.date2String(product.getUpdateTime(),null));
        return productDetailVo;
    }

    //对ProductListVo的进一步封装
    public ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo=new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryotId(product.getCategoryotId());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setSubImages(product.getSubImages());
        productListVo.setPrice(product.getPrice());
        productListVo.setImageHost(PropertisUtil.getStringProperty("product.imageHost","http://192.168.201.131:8080/webApp/"));
        return productListVo;
    }


    //---------------------------------------------------前端接口服务---------------------------------//

    /**
     * 商品的具体内容
     * @param productId 商品id
     * @return
     */
    public ServiceResponse<ProductDetailVo> frontGetProductDetails(Integer productId){
        if (productId!=null){
            Product product=productMapper.selectByPrimaryKey(productId);
            if (product!=null){
                if (product.getStatus()==Constract.ProductStatus.PRODUCT_ONLINE){
                    ProductDetailVo productDetailVo=assembleProductDetailVo(product);
                    return ServiceResponse.createBySuccess("请求成功",productDetailVo);
                }else {
                    return ServiceResponse.createByError("该商品已经下架或者删除");
                }
            }else {
                return ServiceResponse.createByError("该商品已经下架或者删除");
            }
        }else {
            return ServiceResponse.createByError("参数错误,没有传参");
        }
    }

    /**
     * 根据关键字和分类id搜索产品
     * @param keyword 关键字
     * @param categoryId 分类id
     * @param pageNumber
     * @param pageSize
     * @param orderBy 排序类型
     * @return
     */
    public ServiceResponse<PageInfo> frontGetProductByKeyWordAndCategoryId(String keyword,Integer categoryId,Integer pageNumber,
                                                                      Integer pageSize,String orderBy){
        if (StringUtils.isBlank(keyword)&&categoryId==null){
            return ServiceResponse.createByError("参数错误");
        }
        List<Integer> categoryIdsList=new ArrayList<>();
        if (categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null&&StringUtils.isBlank(keyword)){
                //没有该分类并且没有关键字，返回为空
                PageHelper.startPage(pageNumber,pageSize);
                List<ProductListVo> productListVoList=Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVoList);
                return ServiceResponse.createBySuccess(pageInfo);
            }
            categoryIdsList= (List<Integer>) iCategoryService.getCategoryAndDeepChildCategory(category.getId()).getData();
        }
        //关键字不为空,根据关键字和查询条件查询
        if (StringUtils.isNotBlank(keyword)){
            keyword=new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        //排序处理
        PageHelper.startPage(pageNumber,pageSize);
        if (StringUtils.isNotBlank(orderBy)){
            //是否包含这个排序规则
            if (Constract.ProductListOrderBy.ORDER_ASC_DESC.contains(orderBy)){
                String[] orderByArray=orderBy.split("_");
                //排序规则
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        //sql查询
        List<Product> productList=productMapper.selectProductByKeywordAndCategoryId(StringUtils.isBlank(keyword)?null:keyword,
                categoryIdsList.size()==0?null:categoryIdsList);
        List<ProductListVo> productListVoList=Lists.newArrayList();
        for (Product product:productList) {
            ProductListVo productListVo=assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServiceResponse.createBySuccess("请求成功",pageInfo);
    }

    /**
     * 同一分类下的所有产品
     * @param categoryId 分类id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public ServiceResponse getParallelProduct(Integer categoryId,Integer pageNumber,Integer pageSize){
        if (categoryId==null&&!(categoryId instanceof Integer)){
            return ServiceResponse.createByError("参数错误");
        }else {
            PageHelper.startPage(pageNumber,pageSize);
            List<Product> productList=productMapper.selectProductByCategoryId(categoryId);
            List<ProductListVo> productListVoList=Lists.newArrayList();
            for (Product product : productList) {
               ProductListVo productListVo=assembleProductListVo(product);
                productListVoList.add(productListVo);
            }
            PageInfo pageInfo=new PageInfo(productList);
            pageInfo.setList(productListVoList);
            return ServiceResponse.createBySuccess("请求成功",pageInfo);
        }
    }

}
