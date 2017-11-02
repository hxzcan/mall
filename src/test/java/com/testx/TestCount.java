package com.testx;


import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 * Time: 17:25
 */
public class TestCount {

    //TODO 输出结果不准确，有误差 0.060000000000000005
   /* public static void main(String[] args) {
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.22);
        System.out.println(1.21*1.00);
        System.out.println(123.3/100.0);
    }*/

    //TODO 计算结果不准确 0.06000000000000000298372437868010820238851010799407958984375
    /*public static void main(String[] args) {
        BigDecimal b1=new BigDecimal(0.05);
        BigDecimal b2=new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }*/

    //TODO 计算结果准确 0.06
    public static void main(String[] args) {
        BigDecimal b1=new BigDecimal("0.05");
        BigDecimal b2=new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }
}
