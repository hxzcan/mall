package com.ican.controller.frontend;

import com.ican.common.Constract;
import com.ican.common.ResponseCode;
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
 * 用户控制器
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/23 0023
 * Time: 14:13
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param session session
     * @return 用户信息
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session){
        //service->mybatis-dao
        ServiceResponse response=iUserService.login(username,password);
        if (response.isSuccess()){
            session.setAttribute(Constract.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 退出登录
     * @param session
     * @return 退出结果
     */
    @RequestMapping(value = "/loginOut.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> loginOut(HttpSession session){
        session.removeAttribute(Constract.CURRENT_USER);
        return ServiceResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 校验email和用户名
     * @param str 用户名或者email
     * @param type 是用户名还是email
     * @return 校验结果
     */
    @RequestMapping(value = "/checkValid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /**
     * 获取用户信息
     * @param session 保存的session值
     * @return 用户信息
     */
    @RequestMapping(value = "/getUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getUserInfo(HttpSession session){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user!=null){
            return ServiceResponse.createBySuccess(user);
        }else {
            return ServiceResponse.createByError("用户未登录");
        }
    }

    /**
     * 忘记密码
     * @param username 用户名
     * @return 查询出来问题
     */
    @RequestMapping(value = "/forget_password_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetPasswordGetQuestion(String username){
        return iUserService.forgetPasswordGetQuestion(username);
    }

    /**
     * 校验问题答案是否正确
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return 答案存在返回token值，答案不存在返回原因
     */
    @RequestMapping(value = "/forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.forgetCheckAnswer(username,question,answer);
    }

    /**
     * 未登录状态下的 忘记密码重置密码 通过和缓存的token值进行对比
     * @param username 用户名
     * @param newPassword 新的密码
     * @param forgetToken 缓存的token值
     * @return 修改结果
     */
    @RequestMapping(value = "/forget_rest_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetRestPassword(String username,String newPassword,String forgetToken){
        return iUserService.forgetRestPassword(username,newPassword,forgetToken);
    }

    /**
     * 登录状态下 修改密码
     * @param session session值，保存的用户信息
     * @param oldPassword 旧的密码
     * @param newPassword 新的密码
     * @return 修改结果
     */
    @RequestMapping(value = "/login_rest_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> loginRestPassword(HttpSession session,String oldPassword,String newPassword){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            ServiceResponse.createByError("用户未登录");
        }
        return iUserService.loginRestPassword(user,oldPassword,newPassword);
    }

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/update_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> updateUserInfo(HttpSession session,User user){
        User currentUser= (User) session.getAttribute(Constract.CURRENT_USER);
        if (currentUser==null){
            ServiceResponse.createByError("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        user.setRole(currentUser.getRole());
        ServiceResponse response=iUserService.updateUserInfo(user);
        if (response.isSuccess()){
            session.setAttribute(Constract.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 获取用户信息
     * @param session session值
     * @return
     */
    @RequestMapping(value = "/get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getInformation(HttpSession session){
        User user= (User) session.getAttribute(Constract.CURRENT_USER);
        if (user==null){
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"需要强制登录");
        }
        return iUserService.getInformation(user.getId());
    }



    //=========================================app登录===========================================//
    @RequestMapping(value = "/app_login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> appLogin(String username, String password){
        //service->mybatis-dao
        ServiceResponse response=iUserService.login(username,password);
        return response;
    }
}
