package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名查询用户是否存在
     *
     * @param userName 用户名
     * @return
     */
    int checkUserName(String userName);

    /**
     * 根据用户名以及密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户邮箱查询邮箱是否存在
     *
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 获取用户提示问题
     *
     * @param username
     * @return
     */
    String selectQuestion(String username);

    /**
     * 校验问题答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 修改密码
     *
     * @param username
     * @param passwordNew
     * @return
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 横向越权验证
     *
     * @param passwordOld
     * @param id
     * @return
     */
    int checkPassword(@Param("passwordOld") String passwordOld, @Param("id") Integer id);

    /**
     * 验证邮箱是否是当前用户的
     *
     * @param email
     * @param userId
     * @return
     */
    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}