package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
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
@RequestMapping("/user/springsession/")
public class UserSpringSessionController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录接口
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        // 测试全局异常配置
//        int i = 0;
//        int j = 65 / i;
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
//            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
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
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        // 获取token
//        String loginToken = CookieUtil.readLoginToken(request);
//        // 删除cookie
//        CookieUtil.delLoginToken(request, response);
//        // 删除redis中的key
//        RedisShardedPoolUtil.del(loginToken);
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 获取用户详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session, HttpServletRequest request) {
//        // 从request中获取token
//        String loginToken = CookieUtil.readLoginToken(request);
//        // 如果token为空则直接返回
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
//        }
//        // 获取用户的json字符串
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        // 将json转换成对象
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
    }
}
