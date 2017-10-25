package com.ican.dao;

import com.ican.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入新的用户
     * @param record 用户信息
     * @return
     */
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    /**
     * 有选择性的更新数据，即只更新不为空的数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新全部字段
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * 检测用户是否存在
     * @param username
     * @return 返回大于0表示存在
     */
    int checkUserName(String username);

    /**
     * 检测email是否存在
     * @param email
     * @return 返回大于0表示存在
     */
    int checkEmail(String email);

    /**
     * 登录，根据用户名和密码来进行登录
     * 多个参数时使用注解@Param("参数名")，要对应着xml里面的参数
     * @param username
     * @param password
     * @return 用户信息
     */
    User userLogin(@Param("username") String username,@Param("password") String password);

    /**
     * 忘记密码
     * @param username
     * @return 查询出来的问题
     */
    String forgetPasswordGetQuestion(String username);

    /**
     * 校验问题答案，查询出来问题答案
     * @param username 用户名
     * @param question 问题
     * @return 问题答案
     */
    String forgetCheckAnswer(@Param("username") String username,@Param("question") String question);

    /**
     * 更新用户密码
     * @param username 用户名
     * @param newPassword 新的密码
     * @return 返回大于0表示更新成功
     */
    int updatePasswordByUsername(@Param("username") String username,@Param("newPassword") String newPassword);


    /**
     * 根据userId校验密码
     * @param userId 用户id
     * @param oldPassword 密码
     * @return 返回大于0表示存在
     */
    int checkPasswordByUserId(@Param("userId") int  userId,@Param("oldPassword") String oldPassword);

    /**
     * 根据userId校验邮箱
     * @param userId 用户id
     * @param email 邮箱
     * @return
     */
    int checkEmailByUserId(@Param("userId") Integer userId,@Param("email") String email);
}