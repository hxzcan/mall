package com.ican.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * token的缓存,利用guava进行本地缓存
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/24 0024
 * Time: 13:58
 */
public class TokenCache {
    public static Logger logger= LoggerFactory.getLogger(TokenCache.class);

    //initialCapacity 初始化的容量；
    //maximumSize 设置最大容量
    //expireAfterAccess 设置有效期
    //LRU算法，最小
    public static LoadingCache<String,Object> localCache= CacheBuilder.newBuilder()
                    .initialCapacity(1000)
                    .maximumSize(10000)
                    .expireAfterAccess(12, TimeUnit.HOURS)
                    .build(new CacheLoader<String, Object>() {
                        //默认的方法加载实现
                        //党调用方法取值的时候，如果key没有对应的值，就调用这个方法进行加载
                        @Override
                        public String load(String s) throws Exception {
                            return "null";
                        }
                    });

    /**
     * 设置键值
     * @param key 键
     * @param value 值
     */
    public static void  setKey(String key,Object value){
        localCache.put(key,value);
    }

    /**
     * 根据键获取值
     * @param key 键
     * @return 值
     */
    public static Object getValue(String key){
        Object value=null;
        try {
            value=localCache.get(key);
            if (("null").equals(value)){
                return null;
            }else {
                return value;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            logger.error("localCache get value error",e);
        }
        return null;
    }
}
