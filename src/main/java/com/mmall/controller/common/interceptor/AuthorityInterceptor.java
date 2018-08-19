package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * mvc拦截器
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/19
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    private static final String REQUEST_PARAM_BUFFER_SYMBOL = "=";

    /**
     * 校验用户登录状态以及权限
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.info("preHandle");
        // 请求中Cotroller的方法名(强转handler)
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // (解析handlerMethod)获取MethodName
        String methodName = handlerMethod.getMethod().getName();
        // (解析handlerMethod)获取ClassName
        String className = handlerMethod.getBean().getClass().getSimpleName();

        // 解析参数,具体的参数key以及value;
        StringBuffer requestParamBuffer = new StringBuffer();
        // 获取ParameterMap
        Map paramMap = httpServletRequest.getParameterMap();
        // 使用迭代器迭代paramMap
        Iterator it = paramMap.entrySet().iterator();
        // 遍历
        while (it.hasNext()) {
            // 将it的结果强转成Map.Enter类型
            Map.Entry entry = (Map.Entry) it.next();
            // 获取Enter的key
            String mapKey = (String) entry.getKey();
            // 初始化mapValue容器(因为request参数的map里边的value返回的是一个String类型的数组)
            String mapValue = StringUtils.EMPTY;
            // 接受value数组
            Object obj = entry.getValue();
            // 类型判断
            if (obj instanceof String[]) {
                // 强转成String[]类型
                String[] strs = (String[]) obj;
                // 对mapValue进行赋值
                mapValue = Arrays.toString(strs);
            }
            // 组装requestParamBuffer
            requestParamBuffer.append(mapKey).append(REQUEST_PARAM_BUFFER_SYMBOL).append(mapValue);
        }

        // 如果拦截器拦截到登录请求则直接放过
        if (StringUtils.equals(methodName, "login") && StringUtils.equals(className, "UserManageController")) {
            // 拦截到登录请求不打印参数,因为参数有账号密码等敏感信息
            log.info("权限拦截器拦截到请求,className:{}, methodName:{}", className, methodName);
            // 放过登录请求
            return true;
        }
        log.info("权限拦截器拦截到请求,className:{}, methodName:{}, param:{}", className, methodName, requestParamBuffer.toString());
        // 创建空对象
        User user = null;
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        // 如果token不为空则执行以下逻辑
        if (StringUtils.isNotEmpty(loginToken)) {
            // 获取用户的json字符串
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            // 将json转换成对象
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }
        // 判断user是否为空及角色是否为管理员(如果条件为真则返回false)
        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            // 重置response(如果不重置将会报异常)
            httpServletResponse.reset();
            // 设置编码格式(不设置则会乱码)
            httpServletResponse.setCharacterEncoding("UTF-8");
            // 设置返回值类型
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            // 拿到输出对象
            PrintWriter out = httpServletResponse.getWriter();
            // 细化拦截条件
            if (user == null) {
                // 定制富文本上传的拦截
                if (StringUtils.equals(methodName, "richtextImgUpload") && StringUtils.equals(className, "ProductManageController")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员");
                    out.print(JsonUtil.obj2String(resultMap));
                } else {
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            } else {
                // 定制富文本上传的拦截
                if (StringUtils.equals(methodName, "richtextImgUpload") && StringUtils.equals(className, "ProductManageController")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                } else {
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            }
            // 清空流数据
            out.flush();
            // 关闭流
            out.close();
            // 返回结果
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
