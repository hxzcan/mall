package com.ican.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传Service
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/31 0031
 */
public interface IFileService {

    /**
     * 上传文件
     * @param file 文件
     * @param path 路径
     * @return 上传的文件名
     */
    String uploadFile(MultipartFile file, String path);
}

