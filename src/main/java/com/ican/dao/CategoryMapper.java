package com.ican.dao;

import com.ican.pojo.Category;

import java.util.List;


public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * 根据parentId查询出所有的分类，是一个平级的操作，不递归
     * @param parentId
     * @return
     */
    List<Category> selectCategoryByParentId(Integer parentId);

    //=================================前端===========================//

    /**
     * 查找所有的父分类
     * @param parentId 父分类id
     * @return
     */
    List<Category> selectCategoryParentLevel(Integer parentId);
}