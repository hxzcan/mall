package com.ican.service;

import com.ican.common.ServiceResponse;
import com.ican.pojo.User;



/**
 * 用户服务接口层
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/23 0023
 * Time: 14:19
 */
public interface IUserService {


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    ServiceResponse<User> login(String username, String password);

    /**
     * 用户注册
     * @param user 用户信息
     * @return
     */
    ServiceResponse<String> register(User user);

    /**
     * 校验用户名和email是否存在
     * @param str 传入的用户名或者邮箱
     * @param type 是用户名还是邮箱
     * @return
     */
    ServiceResponse<String> checkValid(String str,String type);

    /**
     * 忘记密码
     * @param username 用户名
     * @return 查询出来的问题
     */
    ServiceResponse<String> forgetPasswordGetQuestion(String username);

    /**
     *忘记密码，校验问题答案是否正确
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return 答案存在返回token值，答案不存在返回原因
     */
    ServiceResponse<String> forgetCheckAnswer(String username,String question,String answer);

    /**
     * 未登录状态下的 忘记密码，重置密码 和缓存的token进行对比
     * @param username 用户名
     * @param newPassword 密码
     * @param forgetToken 请求的token值
     * @returna 修改结果
     */
    ServiceResponse<String> forgetRestPassword(String username,String newPassword,String forgetToken);

    /**
     * 登录状态下修改密码
     * @param user 用户信息
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    ServiceResponse<String> loginRestPassword(User user,String oldPassword,String newPassword);

    /**
     * 更新用户信息
     * @param user 用户
     * @return 更新之后的用户信息
     */
    ServiceResponse<User> updateUserInfo(User user);

    /**
     * 强制登录获取用户的信息
     * @param userId 用户id
     * @return 用户的信息
     */
    ServiceResponse<User> getInformation(int userId);

    /**
     * 校验是否是管理员
     * @param user 用户
     * @return 结果码
     */
    ServiceResponse checkAdmin(User user);
}
