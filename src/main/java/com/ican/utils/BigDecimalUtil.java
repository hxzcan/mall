package com.ican.utils;

import java.math.BigDecimal;

/**
 * BigDecimalUtil计算工具，精确计算，用于商业
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 */
public final class BigDecimalUtil {

    private BigDecimalUtil(){

    }

    /**
     * 加法计算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.add(b2);
    }

    /**
     * 减法计算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal subtract(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.subtract(b2);
    }

    /**
     * 乘法计算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal multiply(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.multiply(b2);
    }

    /**
     * 除法计算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal divide(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);//保留两位小数，默认的舍入
    }
}
