package com.ican.common.objectConversion;

import com.ican.pojo.User;
import com.ican.vo.UserVo;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/3 0003
 * Time: 11:36
 */
public class User2UserVo {

    public static UserVo user2UserVo(User user){
        UserVo userVo=new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setPassword(StringUtils.EMPTY);
        userVo.setEmail(user.getEmail());
        userVo.setQuestion(user.getQuestion());
        userVo.setAnswer(user.getAnswer());
        userVo.setRole(user.getRole());
        userVo.setPhone(user.getPhone());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setUpdateTime(user.getUpdateTime());
        return userVo;
    }
}
