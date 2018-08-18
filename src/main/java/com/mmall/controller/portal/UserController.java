package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wb-yxk397023 on 2018/7/30.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录接口
     *
     * @param username
     * @param password
     * @param session
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 用户登出接口
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // 获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 删除cookie
        CookieUtil.delLoginToken(request, response);
        // 删除redis中的key
        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 校验接口
     *
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 将json转换成对象
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
    }

    /**
     * 忘记密码时获取用户提示问题
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.forgetGetQuestion(username);
    }

    /**
     * 校验问题答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.forgetCheckAnswer(username, question, answer);
    }

    /**
     * 修改密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetRestPassword(username, passwordNew, forgetToken);
    }

    /**
     * 修改密码(登录状态)
     *
     * @param request
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpServletRequest request, String passwordOld, String passwordNew) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 将json转换成对象
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录!");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpServletRequest request, User user) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 将json转换成对象
        User currentUser = JsonUtil.string2Obj(userJsonStr, User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录!");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            RedisShardedPoolUtil.setEx(loginToken, JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 获取用户详细信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpServletRequest request) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 将json转换成对象
        User currentUser = JsonUtil.string2Obj(userJsonStr, User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要强制登录status=10!");
        }
        return iUserService.getInformation(currentUser.getId());
    }
}
