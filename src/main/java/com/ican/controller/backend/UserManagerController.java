package com.ican.controller.backend;

import com.ican.common.Constract;
import com.ican.common.ServiceResponse;
import com.ican.pojo.User;
import com.ican.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台管理人员控制器
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/25 0025
 * Time: 13:35
 */
@Controller
@RequestMapping("manager/user")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;


    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @param session session值
     * @return 用户信息
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session){
       ServiceResponse response=iUserService.login(username,password);
        if (response.isSuccess()){
            User user= (User) response.getData();
            //判断是不是管理员
            if (user.getRole()==Constract.role.ROLE_ADMIN){
                session.setAttribute(Constract.CURRENT_USER,user);
            }else {
                return ServiceResponse.createByError("不是管理员，无法登录");
            }
        }
        return response;
    }
}
