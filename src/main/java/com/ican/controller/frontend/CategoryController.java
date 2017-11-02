package com.ican.controller.frontend;

import com.ican.common.ServiceResponse;
import com.ican.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端展示分类
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/1 0001
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 查询出父分类,也就是总分类
     * @return
     */
    @RequestMapping(value = "/category_parentLevel.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse categoryParentLevel(){
        return iCategoryService.categoryFirstLevel();
    }


    /**
     * 查找出子分类
     * @param parentLevelId 父分类的id
     * @return
     */
    @RequestMapping(value = "/category_childLevel.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse categoryChildLevel(Integer parentLevelId){
        return iCategoryService.getChildParallelCategory(parentLevelId);
    }

}
