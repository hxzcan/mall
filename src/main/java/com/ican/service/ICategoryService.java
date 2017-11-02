package com.ican.service;

import com.ican.common.ServiceResponse;

/**
 * 分类管理接口层
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/26 0026
 * Time: 10:10
 */
public interface ICategoryService {

    /**
     * 增加分类管理名称
     * @param categoryName 分类管理名称
     * @param parentId 是否是父节点
     * @return 增加结果
     */
    ServiceResponse addCategory(String categoryName,Integer parentId);

    /**
     * 修改分类的名称
     * @param categoryName 要修改的分类的名称
     * @param categoryId 分类的id
     * @return 修改结果
     */
    ServiceResponse<String> updateCategoryName(String  categoryName,Integer categoryId);

    /**
     * 根据parentId查找所有相同的分类
     * @param parentId
     * @return 所有相同的分类
     */
    ServiceResponse getChildParallelCategory(Integer parentId);

    /**
     * 递归查询所有的分类id
     * @param parentId
     * @return
     */
    ServiceResponse getCategoryAndDeepChildCategory(Integer parentId);

    //=============================前端=============================//
    /**
     * 查找父分类
     * @return
     */
    ServiceResponse categoryFirstLevel();
}
