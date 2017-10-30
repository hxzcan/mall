package com.ican.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置文件的属性
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/27 0027
 * Time: 17:56
 * 静态代码块>普通代码块>构造代码块
 */
public class PropertisUtil {
    private static Logger logger= LoggerFactory.getLogger(PropertisUtil.class);
    private static Properties properties=null;


    public PropertisUtil(){

    }

    //静态代码块加载配置文件
    static {
        //配置文件
        String fileName="system.properties";
        properties=new Properties();
        try {
            //加载配置文件
            properties.load(new InputStreamReader(PropertisUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("加载配置文件的错误",e);
            e.printStackTrace();
        }
    }

    {

    }

    /**
     * 读取配置文件的String类型属性
     * @param key 键
     * @return 值
     */
    public static String getStringProperty(String key){
        String value=properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    /**
     * 读取配置文件String类型的属性设置的有默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getStringProperty(String key,String defaultValue){
        String value=properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return defaultValue;
        }
        return value.trim();
    }

}
