package com.ican.controller.backend;

import com.ican.common.Constract;
import com.ican.common.ResponseCode;
import com.ican.common.ServiceResponse;
import com.ican.pojo.User;
import com.ican.service.ICategoryService;
import com.ican.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 分类管理控制器
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/26 0026
 * Time: 9:40
 */
@Controller
@RequestMapping("manager/category")
public class CategoryManagerController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     * @param session 检查用户是否登录
     * @param categoryName 分类的名称
     * @param parentId 分类的id ，如果没传，默认的是0 父类节点
     * @return 添加结果
     */
    @RequestMapping(value = "/add_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse addCategory(HttpSession session, String categoryName,
                                       @RequestParam(value = "parentId",defaultValue = "0") Integer parentId ){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            //是管理员，添加增加分类接口
            return iCategoryService.addCategory(categoryName,parentId);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 修改分类的名称
     * @param session
     * @param categoryName 分类名称
     * @param categoryId 分类的id
     * @return 修改结果
     */
    @RequestMapping(value = "/update_categoryName.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> updateCategoryName(HttpSession session,String categoryName,Integer categoryId){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            //是管理员，修改分类名称
            return iCategoryService.updateCategoryName(categoryName,categoryId);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 根据categoryId获取分类信息，是平级的属于同一个级别的，不递归
     * @param parentId 分类id 默认是0
     * @return 分类内容
     */
    @RequestMapping(value = "/getChild_parallelCategory.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getChildParallelCategory(HttpSession session,
                                                    @RequestParam(value ="parentId",defaultValue = "0") Integer parentId)
    {
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            //查询子节点的category信息，并且不递归，保持平级
            return iCategoryService.getChildParallelCategory(parentId);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }

    /**
     * 查询节点的id和递归子节点的id
     * @param parentId 分类id 默认是0
     * @return 分类内容
     */
    @RequestMapping(value = "/getCategoryAnd_deepChildCategory.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse getCategoryAndDeepChildCategory(HttpSession session,
                                                    @RequestParam(value ="parentId",defaultValue = "0") Integer parentId)
    {
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，需要登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            //查询节点的id和递归子节点的id
            return iCategoryService.getCategoryAndDeepChildCategory(parentId);
        }else {
            return ServiceResponse.createByError("不是管理员，没有权限");
        }
    }
}
