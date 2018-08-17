package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/17
 */
@Slf4j
public class CookieUtil {
    /**
     * 设置一级域名的domain
     */
    private final static String COOKIE_DOMAIN = ".myfristmmall.com";

    /**
     * 设置cookie
     */
    private final static String COOKIE_NAME = "mmall_login_token";

    /**
     * 设置cookie有效期为一年
     */
    private final static Integer COOKIE_MAX_AGE = 60 * 60 * 24 * 365;

    /**
     * 写入cookie
     *
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token) {
        // 创建cookie对象
        Cookie ck = new Cookie(COOKIE_NAME, token);
        // 设置domain
        ck.setDomain(COOKIE_DOMAIN);
        // 设置路径
        ck.setPath("/");
        // 设置cookie有效期(-1代表永久有效,单位为妙)
        // 如果不设置该属性,cookie将不会被写入硬盘,而是写入内存只在当前页面有效
        ck.setMaxAge(COOKIE_MAX_AGE);
        // 防止脚本攻击(防止信息泄露风险)
        ck.setHttpOnly(true);
        log.info("write cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
        // 将cookie设置到response中
        response.addCookie(ck);
    }

    /**
     * 读取cookie
     *
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request) {
        // 读取cookie数组
        Cookie[] cks = request.getCookies();
        // 如果cookie数组不为空则执行此逻辑
        if (cks != null) {
            // 遍历cookie数组
            for (Cookie ck : cks) {
                log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        // 读取cookie数组
        Cookie[] cks = request.getCookies();
        // 如果cookie数组不为空则执行此逻辑
        if (cks != null) {
            // 遍历cookie数组
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    // 删除cookie(设置为零代表删除cookie)
                    ck.setMaxAge(0);
                    log.info("del cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
