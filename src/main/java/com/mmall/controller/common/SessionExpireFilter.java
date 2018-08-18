package com.mmall.controller.common;

import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 重置session有效期
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/18
 */
public class SessionExpireFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将ServletRequest对象强转成HttpServletRequest对象
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 获取token
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        // 判断token是否为空或者""
        if (StringUtils.isNotEmpty(loginToken)) {
            // 从redis中获取user字符串
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            // 将json字符串序列化成User对象
            User user = JsonUtil.string2Obj(userJsonStr, User.class);
            // 如果user不为空则重置session的时间(即调用expire命令)
            if (user != null) {
                RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
