package com.ican.service.impl;

import com.google.common.collect.Lists;
import com.ican.service.IFileService;
import com.ican.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传服务逻辑
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/31 0031
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    public String uploadFile(MultipartFile file,String path){
        String fileName=file.getOriginalFilename();
        //获取文件的扩展名(从后面往前获取扩展名，)
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        //上传的新名字
        String uploadFileName= UUID.randomUUID()+"."+fileExtensionName;
        logger.info("开始上传文件，上传的文件名:{},上传的扩展名:{},新文件名:{}",fileName,fileExtensionName,uploadFileName);
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);//可写
            fileDir.mkdirs();
        }
        //上传到指定的文件夹下了
        File uploadTargetFile=new File(path+uploadFileName);
        try {
            file.transferTo(uploadTargetFile);
            //TODO 将文件上传到FTP文件服务器
            FTPUtil.uploadFile(Lists.newArrayList(uploadTargetFile));//已经上传到ftp服务器
            //TODO 上传文件到FTP文件之后，删除upload下的文件
            uploadTargetFile.delete();
        } catch (IOException e) {
            logger.info("上传文件失败",e);
            return null;
        }
        return uploadTargetFile.getName();
    }
}
