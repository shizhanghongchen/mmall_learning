package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by wb-yxk397023 on 2018/7/30.
 */
public interface IUserService {

    /**
     * 用户登录接口
     *
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 校验接口
     *
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 忘记密码时获取用户提示问题
     *
     * @param username
     * @return
     */
    ServerResponse<String> forgetGetQuestion(String username);

    /**
     * 校验问题答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> forgetCheckAnswer(String username, String question, String answer);

    /**
     * 修改密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken);

    /**
     * 修改密码(登录状态)
     *
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    ServerResponse<User> updateInformation(User user);

    /**
     * 获取用户详细信息
     *
     * @param userId
     * @return
     */
    ServerResponse<User> getInformation(Integer userId);

    /**
     * 校验是否是管理员
     *
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(User user);
}
