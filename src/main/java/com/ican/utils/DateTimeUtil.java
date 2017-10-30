package com.ican.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 处理时间的工具类
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/30 0030
 */
public class DateTimeUtil {

    private static final String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    //joda包下的处理
    //str-datetime
    /**
     * String转化为时间
     * @param string 字符串
     * @param dateFormatString 时间格式
     * @return 时间格式
     */
    public static Date string2Date(String string,String dateFormatString){

        DateTimeFormatter dateFormat=null;
        DateTime date=null;

        if (StringUtils.isNoneBlank(dateFormatString)){
             dateFormat=DateTimeFormat.forPattern(dateFormatString);
        }else {
            //如果为空就设置为默认的时间格式
            dateFormat=DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT);
        }

        if (StringUtils.isNoneBlank(string)){
           date=dateFormat.parseDateTime(string);
        }
        return date.toDate();
    }

    //datetime-string

    /**
     * 把date转化为字符串
     * @param date 时间
     * @param dateFormatString 转化的格式
     * @return 转化的时间格式
     */
    public static String date2String(Date date,String dateFormatString){
        if (date==null){
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(dateFormatString)){
            dateFormatString=DEFAULT_DATE_FORMAT;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(dateFormatString);
    }
}
