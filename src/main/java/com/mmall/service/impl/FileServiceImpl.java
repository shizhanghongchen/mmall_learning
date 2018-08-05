package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by wb-yxk397023 on 2018/8/4.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 文件分隔符
     */
    private static final String FILE_SEPARATED = ".";

    /**
     * 文件上传服务
     *
     * @param file
     * @param path
     * @return
     */
    @Override
    public String upload(MultipartFile file, String path) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 生成新的文件名
        String uploadFileName = UUID.randomUUID().toString() + FILE_SEPARATED + fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
        // 创建上传路径
        File fileDir = new File(path);
        // 判断当前路径是否存在
        if (!fileDir.exists()) {
            // 赋予权限
            fileDir.setWritable(true);
            // 创建目录
            fileDir.mkdirs();
        }
        // 创建文件
        File targetFile = new File(path, uploadFileName);
        // 上传核心逻辑
        try {
            // 文件上传完成
            file.transferTo(targetFile);
            // 将targetFile上传到FTP服务器上(执行完成表示已经上传成功)
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 上传完成以后,删除uploadx下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        // 返回新的文件名
        return targetFile.getName();
    }
}
