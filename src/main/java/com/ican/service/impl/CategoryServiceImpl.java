package com.ican.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ican.common.ServiceResponse;
import com.ican.dao.CategoryMapper;
import com.ican.pojo.Category;
import com.ican.service.ICategoryService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;


/**
 * 分类管理逻辑服务
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/26 0026
 * Time: 10:10
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class.getName());
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 增加分类管理名称
     * @param categoryName 分类管理名称
     * @param parentId 是否是父节点
     * @return
     */
    @Override
    public ServiceResponse addCategory(String categoryName, Integer parentId) {
        if (parentId==null&& StringUtils.isBlank(categoryName)){
            return ServiceResponse.createIllegal("参数错误，检查是否传值");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(1);
        int resultCount=categoryMapper.insert(category);
        if (resultCount>0){
            return ServiceResponse.createBySuccess("添加分类成功");
        }else {
            return ServiceResponse.createByError("添加分类失败");
        }
    }

    /**
     * 修改分类的名称
     * @param categoryName 要修改的分类的名称
     * @param categoryId 分类的id
     * @return 修改结果
     */
    @Override
    public ServiceResponse<String> updateCategoryName(String categoryName, Integer categoryId) {
        if (categoryId==null&& StringUtils.isBlank(categoryName)){
            return ServiceResponse.createIllegal("参数错误，检查是否传值");
        }
        Category category=new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int resultCount=categoryMapper.updateByPrimaryKeySelective(category);
        if (resultCount>0){
            return ServiceResponse.createBySuccess("修改成功");
        }else {
            return ServiceResponse.createByError("修改失败");
        }
    }

    /**
     * 根据parentId查找所有相同的分类
     * @param parentId
     * @return
     */
    @Override
    public ServiceResponse<List<Category>> getChildParallelCategory(Integer parentId){
        List<Category> categoryList=categoryMapper.selectCategoryByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到相关的分类");
        }
        return ServiceResponse.createBySuccess("请求成功",categoryList);
    }

    /**
     * 递归查询所有的分类id
     * @param parentId
     * @return
     */
    public ServiceResponse getCategoryAndDeepChildCategory(Integer parentId){
        List<Integer> allcategoryId= Lists.newArrayList();//guava封装的集合
        Set<Category> categories= Sets.newHashSet();
        findChildCategory(categories,parentId);
        if (parentId!=null){
            for (Category category:categories) {
                allcategoryId.add(category.getId());
            }
        }
        return ServiceResponse.createBySuccess("请求成功",allcategoryId);
    }

    //==============================================================前端接口逻辑============================//

    /**
     * 查找父分类
     * @return
     */
    public ServiceResponse categoryFirstLevel(){
        List<Category> categories=categoryMapper.selectCategoryParentLevel(0);
        if (categories!=null&&categories.size()>0){
            return ServiceResponse.createBySuccess("请求成功",categories);
        }else {
            return ServiceResponse.createByError("没有分类");
        }
    }



    //=====================================公共逻辑============================================//

    //通过chon重写hashcode和equals方法来判断两个对象是否相同，如果相同则equals返回true，那么他们hashcode要返回相同
    //但是如果hashcode相同，他们的equals返回不一定为true
    //递归算法，算出子节点
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer parentId){
        Category category=categoryMapper.selectByPrimaryKey(parentId);
        if (category!=null){
            categorySet.add(category);
        }
        List<Category> categories=categoryMapper.selectCategoryByParentId(parentId);
        for (Category categoryItem:categories){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

}
