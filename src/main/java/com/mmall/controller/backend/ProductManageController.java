package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wb-yxk397023 on 2018/8/4.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    /**
     * 新增/更新产品(后台)
     *
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(Product product) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iProductService.saveOrUpdateProduct(product);
    }

    /**
     * 修改产品销售状态(后台)
     *
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iProductService.setSaleStatus(productId, status);
    }

    /**
     * 获取产品详情(后台)
     *
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(Integer productId) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iProductService.manageProductDetail(productId);
    }

    /**
     * 获取产品list页(后台)
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iProductService.getProductList(pageNum, pageSize);
    }

    /**
     * 商品搜索(后台)
     *
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(String productName, Integer productId
            , @RequestParam(value = "pageNum", defaultValue = "1") int pageNum
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    /**
     * SpringMVC文件上传(后台)
     *
     * @param file
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest httpServletRequest) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        // 执行上传
        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    /**
     * 富文本图片上传(后台)
     *
     * @param httpServletRequest
     * @param file
     * @param response
     * @return
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest httpServletRequest, HttpServletResponse response) {
        // 登录状态以及权限验证如果全部通过拦截器验证则执行此逻辑
        // 声明Map
        Map resultMap = Maps.newHashMap();

        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        // 判断targetFileName,如果为空的情况
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        // 上传成功的情况
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("file_path", url);
        resultMap.put("msg", "上传成功");
        resultMap.put("success", true);
        // 上传成功后处理response的返回信息
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }
}