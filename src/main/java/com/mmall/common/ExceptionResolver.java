package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/19
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    /**
     * 全局异常处理方法
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        log.error("{} Exception", httpServletRequest.getRequestURI(), e);
        // 创建视图对象(当使用jackson2.x的时候使用MappingJackson2JsonView)
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        // 添加返回对象的状态
        modelAndView.addObject("status", ResponseCode.ERROR.getCode());
        // 添加返回对象的描述
        modelAndView.addObject("msg", "接口发生异常,请查看服务器日志.");
        // 添加返回对象的具体信息
        modelAndView.addObject("data", e.toString());
        // 返回modelAndView
        return modelAndView;
    }
}
