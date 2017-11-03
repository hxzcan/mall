package com.ican.service.impl;

import com.ican.common.Constract;
import com.ican.common.ServiceResponse;
import com.ican.common.TokenCache;
import com.ican.common.objectConversion.User2UserVo;
import com.ican.dao.UserMapper;
import com.ican.pojo.User;
import com.ican.service.IUserService;
import com.ican.utils.MD5Util;
import com.ican.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 用户服务逻辑层
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/23 0023
 * Time: 14:20
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount=userMapper.checkUserName(username);
        if (resultCount==0){
           return ServiceResponse.createByError("用户不存在");
        }
        //密码登录MD5
        String MD5Password=MD5Util.MD5EncodeUTF8(password);
        User user=userMapper.userLogin(username,MD5Password);
        if (user==null){
            return  ServiceResponse.createByError("密码错误");
        }
        //生成登录的UUID
        String app_login_token=UUID.randomUUID().toString();
        //放进缓存中
        TokenCache.setKey(app_login_token,user);
        UserVo userVo= User2UserVo.user2UserVo(user);
        userVo.setToken(app_login_token);
        return  ServiceResponse.createBySuccess("登录成功",userVo);
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return
     */
    @Override
    public ServiceResponse<String> register(User user) {
        //用户名检查
        ServiceResponse checkValidResponse=this.checkValid(user.getUsername(),Constract.USERNAME);
        if (!checkValidResponse.isSuccess()){
            return checkValidResponse;
        }
        //邮箱检查
        checkValidResponse=this.checkValid(user.getEmail(),Constract.EMAIL);
        if (!checkValidResponse.isSuccess()){
            return checkValidResponse;
        }

        user.setRole(Constract.role.ROLE_CUSTOMER);//设置角色
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUTF8(user.getPassword()));
        int resultCount=userMapper.insert(user);
        if (resultCount==0){
            return ServiceResponse.createByError("注册失败");
        }
        return ServiceResponse.createBySuccess("注册成功");
    }

    /**
     * 校验用户名和email
     * @param str 传入的用户名或者邮箱
     * @param type 是用户名还是邮箱
     * @return
     */
    public ServiceResponse<String> checkValid(String str,String type){
        System.out.println(type);
        if (StringUtils.isNoneBlank(type)){
            if (Constract.EMAIL.equals(type)){
                int resultCount=userMapper.checkEmail(str);
                if (resultCount>0){
                   return ServiceResponse.createByError("email已被使用");
                }
            }
            if (Constract.USERNAME.equals(type)){
                int resultCount=userMapper.checkUserName(str);
                if (resultCount>0){
                   return ServiceResponse.createByError("用户已经存在");
                }
            }
        }else {
            return ServiceResponse.createIllegal("参数错误");
        }
        return ServiceResponse.createBySuccess("校验成功");
    }

    /**
     * 忘记密码 获取问题信息
     * @param username 用户名
     * @return 查询出来问题
     */
    @Override
    public ServiceResponse<String> forgetPasswordGetQuestion(String username) {
        ServiceResponse resultResponse=this.checkValid(username,Constract.USERNAME);
        if (resultResponse.isSuccess()){
           return ServiceResponse.createByError("用户不存在");
        }

        String question=userMapper.forgetPasswordGetQuestion(username);
        if (StringUtils.isNoneBlank(question)){
            return ServiceResponse.createBySuccess("请求成功",question);
        }else {
            return ServiceResponse.createByError("没有设置问题");
        }
    }

    /**
     *忘记密码，校验问题答案是否正确
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return 答案存在返回token值，答案不存在返回原因
     */
    @Override
    public ServiceResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        String checkAnswer=userMapper.forgetCheckAnswer(username,question);
        if (StringUtils.isNoneBlank(checkAnswer)){
            if (checkAnswer.equals(answer)){
                //生成一个UUID，token值;利用guava来进行本地缓存，设置一个有效期
                String forgetToken= UUID.randomUUID().toString();
                //设置Token缓存
                TokenCache.setKey(Constract.TOKEN_PREFIX+username,forgetToken);

               return ServiceResponse.createBySuccess(forgetToken);
            }else {
                return ServiceResponse.createByError("答案不正确");
            }
        }
        return ServiceResponse.createByError("没有设置答案");
    }

    /**
     * 未登录状态下的 忘记密码，重置密码 和缓存的token进行对比
     * @param username 用户名
     * @param newPassword 密码
     * @param forgetToken 请求的token值
     * @return 修改结果
     */
    @Override
    public ServiceResponse<String> forgetRestPassword(String username, String newPassword, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)){
            return ServiceResponse.createIllegal("参数错误，token值是空的");
        }

        ServiceResponse resultResponse=this.checkValid(username,Constract.USERNAME);
        if (resultResponse.isSuccess()){
            return ServiceResponse.createByError("用户不存在");
        }

        String token= (String) TokenCache.getValue(Constract.TOKEN_PREFIX+username);
        if (StringUtils.isBlank(token)){
            return ServiceResponse.createByError("Token过期或者无效");
        }

        if (StringUtils.equals(token,forgetToken)){
            //加密
            String MD5NewPassword=MD5Util.MD5EncodeUTF8(newPassword);
            //更新数据库的密码
            int resultCount=userMapper.updatePasswordByUsername(username,MD5NewPassword);
            if (resultCount>0){
                return ServiceResponse.createBySuccess("修改成功");
            }else {
                return ServiceResponse.createByError("修改失败");
            }
        }else{
            return ServiceResponse.createByError("Token错误，请重新获取修改密码的Token值");
        }
    }

    /**
     * 登录状态下修改密码
     * @param user 用户信息
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    @Override
    public ServiceResponse<String> loginRestPassword(User user, String oldPassword, String newPassword) {
        //防止横向越权，要校验一下这个用户的旧密码，一定是指定的这个用户，因为我们会查询出一个count(1),
        //如果是true,这个count(1)>0
        int resultCount=userMapper.checkPasswordByUserId(user.getId(),MD5Util.MD5EncodeUTF8(oldPassword));
        if (resultCount>0){
            String MD5newPassword=MD5Util.MD5EncodeUTF8(newPassword);
            user.setPassword(MD5newPassword);
            int updateResult=userMapper.updateByPrimaryKeySelective(user);
            if (updateResult>0){
              return ServiceResponse.createBySuccess("密码已经修改");
            }else {
                return ServiceResponse.createByError("修改失败");
            }
        }else {
            return ServiceResponse.createByError("旧密码错误");
        }
    }

    /**
     * 更新用户信息
     * @param user 用户
     * @return 更新之后的用户信息
     */
    @Override
    public ServiceResponse<User> updateUserInfo(User user) {
        //用户名是不能被修改的
        //修改的时候要校验邮箱，校验新的密码是不是已经存在，并且存在的email如果相同的话，不能是我们当前的这个用户
        int emailCount=userMapper.checkEmailByUserId(user.getId(),user.getEmail());
        if (emailCount>0){
            return  ServiceResponse.createByError("邮箱已被使用，请更换邮箱");
        }
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount=userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount>0){
            return ServiceResponse.createBySuccess("更新成功",user);
        }
        return ServiceResponse.createByError("更新失败");
    }

    /**
     * 强制登录获取用户的信息
     * @param userId 用户id
     * @return 用户的信息
     */
    @Override
    public ServiceResponse<User> getInformation(int userId) {
        User user=userMapper.selectByPrimaryKey(userId);
        if (user==null){
            return ServiceResponse.createByError("用户不存在");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登录成功",user);
    }

    /**
     * 校验是否是管理员
     * @param user 用户
     * @return 结果码
     */
    public ServiceResponse checkAdmin(User user){
        if (user!=null&&user.getRole()==Constract.role.ROLE_ADMIN){
            return ServiceResponse.createBySuccess();
        }
        return ServiceResponse.createByError();
    }

}
