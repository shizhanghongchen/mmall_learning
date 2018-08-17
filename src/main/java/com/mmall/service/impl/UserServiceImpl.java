package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by wb-yxk397023 on 2018/7/30.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    /**
     * TokenCache过期时间
     */
    private static final Integer TOKEN_CACHE_OUT_TIME = 60 * 60 * 12;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录接口
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        // 校验用户名
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在!");
        }
        // MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        // 登录
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误!");
        }
        // 清空密码
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功!", user);
    }

    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> register(User user) {
        // 校验用户名和邮箱
        ServerResponse<String> checkValid = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!checkValid.isSuccess()) {
            return checkValid;
        }
        checkValid = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!checkValid.isSuccess()) {
            return checkValid;
        }
        // 设置默认用户类型
        user.setRole(Const.Role.ROLE_CUSTOMER);
        // MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        // 新建用户
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return ServerResponse.createByErrorMessage("注册失败!");
        }
        return ServerResponse.createBySuccessMessage("注册成功!");
    }

    /**
     * 用户校验接口
     *
     * @param str
     * @param type
     * @return
     */
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        // 判断类型非空
        if (StringUtils.isNotBlank(type)) {
            // 判断类型是否为USERNAME
            if (Const.USERNAME.equals(type)) {
                // 查询用户名是否存在
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在!");
                }
            }
            // 判断类型是否为EMAIL
            if (Const.EMAIL.equals(type)) {
                // 查询邮箱是否存在
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已存在!");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误!");
        }
        return ServerResponse.createBySuccessMessage("校验成功!");
    }

    /**
     * 忘记密码时获取用户提示问题
     *
     * @param username
     * @return
     */
    @Override
    public ServerResponse<String> forgetGetQuestion(String username) {
        ServerResponse<String> response = this.checkValid(username, Const.USERNAME);
        if (response.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在!");
        }

        String question = userMapper.selectQuestion(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的!");
    }

    /**
     * 校验问题答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        int checkAnswer = userMapper.checkAnswer(username, question, answer);
        if (checkAnswer > 0) {
            String forgetToken = UUID.randomUUID().toString();
            RedisPoolUtil.setEx(Const.TOKEN_PREFIX + username, forgetToken, TOKEN_CACHE_OUT_TIME);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误!");
    }

    /**
     * 修改密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @Override
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误,token需要传递!");
        }
        ServerResponse<String> response = this.checkValid(username, Const.USERNAME);
        if (response.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在!");
        }
        String token = RedisPoolUtil.get(Const.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或者过期!");
        }
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int passwordByUsername = userMapper.updatePasswordByUsername(username, md5Password);
            if (passwordByUsername > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功!");
            }
        } else {
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token!");
        }
        return ServerResponse.createByErrorMessage("修改密码失败!");
    }

    /**
     * 修改密码(登录状态)
     *
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        // 横向越权验证
        int checkPassword = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (checkPassword == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误!");
        }

        user.setPassword(passwordNew);
        int updateByPrimaryKeySelective = userMapper.updateByPrimaryKeySelective(user);
        if (updateByPrimaryKeySelective > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功!");
        }
        return ServerResponse.createByErrorMessage("密码更新失败!");
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public ServerResponse<User> updateInformation(User user) {
        int emailByUserId = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (emailByUserId > 0) {
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新!");
        }

        User updateUser = new User();
        // id
        updateUser.setId(user.getId());
        // 邮箱
        updateUser.setEmail(user.getEmail());
        // 电话
        updateUser.setPhone(user.getPhone());
        // 问题
        updateUser.setQuestion(user.getQuestion());
        // 答案
        updateUser.setAnswer(user.getAnswer());

        int selective = userMapper.updateByPrimaryKeySelective(updateUser);
        if (selective > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功!", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败!");
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            ServerResponse.createByErrorMessage("找不到当前用户!");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    /**
     * 校验是否是管理员
     *
     * @param user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
