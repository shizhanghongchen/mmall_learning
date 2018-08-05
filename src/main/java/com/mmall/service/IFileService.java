package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wb-yxk397023 on 2018/8/4.
 */
public interface IFileService {

    /**
     * 文件上传服务
     *
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);
}
