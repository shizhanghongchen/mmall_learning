package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb-yxk397023 on 2018/8/4.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    /**
     * ImageHosts key
     */
    private static final String FTP_SERVER_HTTP_PREFIX_KEY = "ftp.server.http.prefix";

    /**
     * ImageHosts value
     */
    private static final String FTP_SERVER_HTTP_PREFIX_VALUE = "http://img.happymmall.com/";

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 新增/更新商品(后台)
     *
     * @param product
     * @return
     */
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null) {
                int primaryKey = productMapper.updateByPrimaryKey(product);
                if (primaryKey > 0) {
                    return ServerResponse.createBySuccessMessage("更新产品成功!");
                }
                return ServerResponse.createByErrorMessage("更新产品失败!");
            } else {
                int insert = productMapper.insert(product);
                if (insert > 0) {
                    return ServerResponse.createBySuccessMessage("新增产品成功!");
                }
                return ServerResponse.createByErrorMessage("新增产品失败!");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确!");
    }

    /**
     * 修改产品销售状态(后台)
     *
     * @param productId
     * @param status
     * @return
     */
    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int selective = productMapper.updateByPrimaryKeySelective(product);
        if (selective > 0) {
            return ServerResponse.createBySuccess("修改产品销售状态成功!");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败!");
    }

    /**
     * 获取产品详情(后台)
     *
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除!");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 获取产品list页(后台)
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize) {
        // startPage--start
        PageHelper.startPage(pageNum, pageSize);
        // 填充自己的sql查询逻辑
        List<Product> productList = productMapper.selectList();
        // 声明容器
        List<ProductListVo> productListVos = Lists.newArrayList();
        // 组装ProductListVo
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVos.add(productListVo);
        }
        // pageHelper-收尾
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVos);
        // 返回
        return ServerResponse.createBySuccess(pageInfo);
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
    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        // startPage--start
        PageHelper.startPage(pageNum, pageSize);
        // sql查询前置条件
        if (productName != null) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        // 填充自己的sql查询逻辑
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        // 声明容器
        List<ProductListVo> productListVos = Lists.newArrayList();
        // 组装ProductListVo
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVos.add(productListVo);
        }
        // pageHelper-收尾
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVos);
        // 返回
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 获取产品详情(前台)
     *
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除!");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除!");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 产品列表 搜索 动态排序(前台)
     *
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        // 关键字与分类id为空的情况
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        // 创建容器
        List<Integer> categoryIdList = new ArrayList<Integer>();
        // 分类id不为空的情况
        if (categoryId != null) {
            // 查询category对象
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            // category对象为空且关键字为空的情况(没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错)
            if (category == null && StringUtils.isBlank(keyword)) {
                // 开始分页处理
                PageHelper.startPage(pageNum, pageSize);
                // 创建空的容器
                List<ProductListVo> productListVoList = Lists.newArrayList();
                // 分页处理
                PageInfo pageInfo = new PageInfo(productListVoList);
                // 返回空结果
                return ServerResponse.createBySuccess(pageInfo);
            }
            // category对象不为空则执行查询逻辑获取结果集
            categoryIdList = iCategoryService.getCategoryAndDeepChildrenCategory(category.getId()).getData();
        }
        // 关键字不为空
        if (StringUtils.isNotBlank(keyword)) {
            // 拼接关键字
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        // 开始分页处理
        PageHelper.startPage(pageNum, pageSize);
        // 根据orderBy排序处理
        if (StringUtils.isNotBlank(orderBy)) {
            // orderBy参数比较
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                // 拆分orderBy
                String[] orderByArray = orderBy.split("_");
                // 重新拼装orderBy
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        // 查询结果集
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size() == 0 ? null : categoryIdList);
        // 创建容器
        List<ProductListVo> productListVoList = Lists.newArrayList();
        // 遍历查询结果
        for (Product product : productList) {
            // 类型转换
            ProductListVo productListVo = assembleProductListVo(product);
            // 组装结果
            productListVoList.add(productListVo);
        }
        // 执行分页逻辑
        PageInfo pageInfo = new PageInfo(productList);
        // 设置返回结果集
        pageInfo.setList(productListVoList);
        // 返回
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * Product DTO 转 VO
     *
     * @param product
     * @return
     */
    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        if (product != null) {
            productDetailVo.setId(product.getId());
            productDetailVo.setCategoryId(product.getCategoryId());
            productDetailVo.setName(product.getName());
            productDetailVo.setSubtitle(product.getSubtitle());
            productDetailVo.setMainImage(product.getMainImage());
            productDetailVo.setSubImages(product.getSubImages());
            productDetailVo.setDetail(product.getDetail());
            productDetailVo.setPrice(product.getPrice());
            productDetailVo.setStock(product.getStock());
            productDetailVo.setStatus(product.getStatus());
            productDetailVo.setImageHost(PropertiesUtil.getProperty(FTP_SERVER_HTTP_PREFIX_KEY, FTP_SERVER_HTTP_PREFIX_VALUE));
            Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
            if (category == null) {
                productDetailVo.setParentCategoryId(0);
            } else {
                productDetailVo.setParentCategoryId(category.getParentId());
            }
            productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
            productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        }
        return productDetailVo;
    }

    /**
     * ProductList DTO 转 VO
     *
     * @param product
     * @return
     */
    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        if (product != null) {
            productListVo.setId(product.getId());
            productListVo.setCategoryId(product.getCategoryId());
            productListVo.setImageHost(PropertiesUtil.getProperty(FTP_SERVER_HTTP_PREFIX_KEY, FTP_SERVER_HTTP_PREFIX_VALUE));
            productListVo.setMainImage(product.getMainImage());
            productListVo.setName(product.getName());
            productListVo.setPrice(product.getPrice());
            productListVo.setStatus(product.getStatus());
            productListVo.setSubtitle(product.getSubtitle());
        }
        return productListVo;
    }
}
