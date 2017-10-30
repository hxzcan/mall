package com.ican.common;

/**
 * 常量类
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/23 0023
 * Time: 16:00
 */
public class Constract {
    public static final String CURRENT_USER="CURRENT_USER";//session
    public static final String EMAIL="email";//email
    public static final String USERNAME="userName";//用户名
    public static final String TOKEN_PREFIX="token_";//token前缀

    //角色
    public interface role{
        int ROLE_CUSTOMER=1;//普通用户
        int ROLE_ADMIN=0;//管理员用户
    }

    public interface ProductStatus{
        int PRODUCT_ONLINE=1;//在卖
        int PRODUCT_OFF_THE_SHELF=2;//下架
        int PRODUCT_DELETE=3;//删除
    }
}
