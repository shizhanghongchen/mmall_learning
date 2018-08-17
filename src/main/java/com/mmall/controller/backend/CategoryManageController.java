package com.mmall.controller.backend;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wb-yxk397023 on 2018/8/2.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加类目
     *
     * @param request
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisPoolUtil.get(loginToken);
        // 将json转换成对象
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录!");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限!");
    }

    /**
     * 更新类目名称
     *
     * @param request
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisPoolUtil.get(loginToken);
        // 将json转换成对象
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.setCategoryName(categoryId, categoryName);
        }
        return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限!");
    }

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     *
     * @param request
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisPoolUtil.get(loginToken);
        // 将json转换成对象
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限!");
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     *
     * @param request
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request,
                                                             @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        // 从request中获取token
        String loginToken = CookieUtil.readLoginToken(request);
        // 如果token为空则直接返回
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息.");
        }
        // 获取用户的json字符串
        String userJsonStr = RedisPoolUtil.get(loginToken);
        // 将json转换成对象
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getCategoryAndDeepChildrenCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限!");
    }
}
