package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wb-yxk397023 on 2018/8/2.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加类目
     *
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iCategoryService.addCategory(categoryName, parentId);
    }

    /**
     * 更新类目名称
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iCategoryService.setCategoryName(categoryId, categoryName);
    }

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iCategoryService.getCategoryAndDeepChildrenCategory(categoryId);
    }
}
