package com.ican.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ican.common.Constract;
import com.ican.common.ServiceResponse;
import com.ican.dao.CartMapper;
import com.ican.dao.ProductMapper;
import com.ican.pojo.Cart;
import com.ican.pojo.Product;
import com.ican.service.ICartService;
import com.ican.utils.BigDecimalUtil;
import com.ican.utils.PropertisUtil;
import com.ican.vo.CartProductVo;
import com.ican.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 购物车逻辑服务接口
 * 商业计算需要使用BigDecimal的String构造器
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 * Time: 16:10
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加商品到购物车
     * @param userId 用户id
     * @param count 数量
     * @param productId 产品id
     * @return
     */
    public ServiceResponse addProduct2Cart(Integer userId,Integer count, Integer productId){
        //先查询购物车中是否有这个商品
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null){
            //如果为空说明购物车中没有改商品，需要添加进去
            if (checkedQuantityMoreThanStock(productId,count)){
                Cart cartItem=new Cart();
                cartItem.setUserId(userId);
                cartItem.setProductId(productId);
                cartItem.setQuantity(count);
                cartItem.setChecked(Constract.CartProductStatus.UNCHECKED);
                int addCount=cartMapper.insertSelective(cartItem);
                if (addCount>0){
                    return ServiceResponse.createBySuccess("添加购物车成功");
                }else {
                    return ServiceResponse.createByError("添加购物车失败");
                }
            }else {
                return ServiceResponse.createByError("库存不足，添加到购物车的数量太多");
            }
        }else {
            //如果购物车已经有了，则先查询以前的数量，再加上新的
            count=cart.getQuantity()+count;
            System.out.println("添加的数量"+count);
            if (checkedQuantityMoreThanStock(productId,count)){
                cart.setQuantity(count);
                cartMapper.updateByPrimaryKeySelective(cart);
                return ServiceResponse.createBySuccess("添加成功");
            }else {
             return ServiceResponse.createByError("库存不足，添加到购物车的数量太多");
            }
        }
    }


    /**
     * 更新购物车中的数量
     * @param userId
     * @param count
     * @param productId
     * @return
     */
    public ServiceResponse updateProduct2Cart(Integer userId,Integer count,Integer productId){
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart!=null){
            if (checkedQuantityMoreThanStock(productId,count)){
                cart.setQuantity(count);
               int updateCount=cartMapper.updateByPrimaryKeySelective(cart);
                if (updateCount>0){
                    return this.getProductFromCart(userId);
                }else {
                    return ServiceResponse.createByError("更新失败");
                }
            }else {
                return ServiceResponse.createByError("库存不足，添加到购物车的数量太多");
            }
        }else {
            return ServiceResponse.createByError("找不到指定的商品");
        }
    }


    /**
     * 删除购物车中的商品 多个商品以,分隔
     * @param userId
     * @param productIds
     * @return
     */
    public ServiceResponse deleteProductFromCart(Integer userId,String productIds){
        if (userId==null|| StringUtils.isBlank(productIds)){
            return ServiceResponse.createByError("传参错误");
        }
        List<String> productIdList= Splitter.on(",").splitToList(productIds);//guava的分割
        int deleteCount=cartMapper.deleteCartByUserIdAndProductIds(userId,productIdList);
        if (deleteCount>0){
            return this.getProductFromCart(userId);
        }else {
            return ServiceResponse.createByError("删除失败");
        }
    }

    /**
     * 用户查看购物车
     * @param userId
     * @return
     */
    public ServiceResponse getProductFromCart(Integer userId){
        if (userId==null){
            return ServiceResponse.createByError("传参错误");
        }
        CartVo cartVo=this.getCartVo(userId);
        return ServiceResponse.createBySuccess("请求成功",cartVo);
    }

    /**
     * 选中或者不选中
     * @param userId 用户id
     * @param checked 全选：1，全反选：0
     * @return
     */
    public ServiceResponse selectOrUnSelect(Integer userId,Integer checked,Integer productId){
        if (userId==null||checked==null){
            return ServiceResponse.createByError("传参错误");
        }
        cartMapper.checkedOrUnCheckedProduct(userId,checked,productId);
        return this.getProductFromCart(userId);
    }

    /**
     * 查询购物车里面商品的数量
     * @param userId
     * @return
     */
    public ServiceResponse getCountFromCart(Integer userId){
        if (userId==null){
            return ServiceResponse.createByError("传参错误");
        }
        Map<String,Integer> countMap= Maps.newHashMap();
       /* final List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        int productCount=0;
        if(CollectionUtils.isNotEmpty(cartList)){
            for (Cart cart : cartList) {
                productCount=productCount+cart.getQuantity();
            }
            countMap.put("productNumber",productCount);
            return ServiceResponse.createBySuccess("购物车中的数量为",countMap);
        }else {
            countMap.put("productNumber",0);
            return ServiceResponse.createBySuccess("购物车中为空",countMap);
        }*/

        //sql语句计算总数
        final int productCount=cartMapper.selectProductCount(userId);
        countMap.put("productNumber",productCount);
        return ServiceResponse.createBySuccess("购物车中的数量",countMap);
    }

    //===================================================================================================//
    /**
     * 把用户的购物车封装成一个CartVo
     * @param userId 用户id
     * @return
     */
    private CartVo getCartVo(Integer userId){
        CartVo cartVo=new CartVo();
        //查出用户购物车添加的所有商品
        List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        //整理成CartProductVo
        BigDecimal totalPrice=new BigDecimal("0");//总价初始化
        List<CartProductVo> cartProductVoList= Lists.newArrayList();
        for (Cart cart : cartList) {
            CartProductVo cartProductVo=new CartProductVo();
            cartProductVo.setCartId(cart.getId());
            cartProductVo.setUserId(cart.getUserId());
            cartProductVo.setProductId(cart.getProductId());
            cartProductVo.setChecked(cart.getChecked());
            //根据id查询商品信息
            Product product=productMapper.selectByPrimaryKey(cart.getProductId());
            if (product!=null){
                cartProductVo.setProductName(product.getName());
                cartProductVo.setProductMainImage(product.getMainImage());
                cartProductVo.setProductPrice(product.getPrice());
                cartProductVo.setProductStatus(product.getStatus());
                cartProductVo.setProductStock(product.getStock());
                cartProductVo.setProductSubtitle(product.getSubtitle());
                //判断库存
                int byLimitCount=0;
                //如果库存的数量大于等于添加到购物车的数量
                if (product.getStock()>=cart.getQuantity()){
                    //库存充足
                    byLimitCount=cart.getQuantity();
                    cartProductVo.setLimitQuantity(Constract.CartProductStatus.LIMIIT_SUCCESS);
                }else {
                    //库存不足。购买值最大是库存的数量
                    byLimitCount=product.getStock();
                    cartProductVo.setLimitQuantity(Constract.CartProductStatus.LIMIT_FAIL);
                    //更新购物车中的购买数量
                    Cart updateCart=new Cart();
                    updateCart.setQuantity(byLimitCount);
                    updateCart.setId(cart.getId());
                    cartMapper.updateByPrimaryKeySelective(updateCart);
                }
                //总数量
                cartProductVo.setQuantity(byLimitCount);
                //计算总价
                cartProductVo.setProductTotalPrice(BigDecimalUtil.multiply(product.getPrice().doubleValue(),
                        cartProductVo.getQuantity()));
            }
            //计算用户购物车总的价格 先判断是否勾选
            if (cartProductVo.getChecked()==Constract.CartProductStatus.CHECKED){
                totalPrice=BigDecimalUtil.add(totalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
            }
            cartProductVoList.add(cartProductVo);
        }
        //计算用户购物车总的价格
      /*  for (CartProductVo cpv : cartProductVoList) {
            //如果勾选
            if (cpv.getChecked()==Constract.CartProductStatus.CHECKED){
                totalPrice=BigDecimalUtil.add(totalPrice.doubleValue(),cpv.getProductTotalPrice().doubleValue());
            }
        }*/
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllCheck(this.getCartAllCheckedStatus(userId));
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setImageHost(PropertisUtil.getStringProperty("product.imageHost"));
        return cartVo;
    }

    /**
     * 判断购物车中的商品是否全选,如果有一个没有勾选就是false,查询的时候查没有勾选的
     * @return
     */
    private Boolean getCartAllCheckedStatus(Integer userId){
        if (userId==null){
            return false;
        }else {
            return cartMapper.selectProductCheckedStatusByUserId(userId)==0;
        }
    }

    /**
     * 检查添加的商品数量是否大于库存
     * @param productId
     * @param count
     * @return
     */
    private boolean checkedQuantityMoreThanStock(Integer productId,Integer count){
        Product  product=productMapper.selectByPrimaryKey(productId);
        System.out.println("库存数量："+product.getStock());
        if (product!=null){
            if (product.getStock()>=count){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
