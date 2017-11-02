package com.ican.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Ftp文件服务器工具
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/31 0031
 */
public class FTPUtil {
    private static final Logger logger= LoggerFactory.getLogger(FTPUtil.class);

    private  static final String FTP_IP=PropertisUtil.getStringProperty("ftp.serverIp");
    private  static final String FTP_USER=PropertisUtil.getStringProperty("ftp.username");
    private  static final String FTP_PASSWORD=PropertisUtil.getStringProperty("ftp.password");

    private String ip;
    private int port;
    private String userName;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String userName, String pwd) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.pwd = pwd;
    }

    /**
     * 上传文件
     * @param fileList
     * @return
     * @throws IOException
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil=new FTPUtil(FTP_IP,21,FTP_USER,FTP_PASSWORD);
        logger.info("开始连接FTP服务器");
        boolean result=ftpUtil.uploadFile("/img",fileList);
        logger.info("结束上传，上传结果:{}",result);
        return result;
    }

    /**
     * 上传文件
     * @param remotePath 远程地址
     * @param fileList 文件
     * @return
     */
    private  boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean isUpload=true;
        FileInputStream fileInputStream=null;
        //连接FTP服务器
        if (isConncet2FTPServer(this.getIp(),this.getPort(),this.getUserName(),this.getPwd())){
            try {
                ftpClient.changeWorkingDirectory(remotePath);//换一个新的文件夹
                ftpClient.setBufferSize(1024);//设置缓冲区
                ftpClient.setControlEncoding("UTF-8");//设置编码
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//二进制编码
                ftpClient.enterLocalPassiveMode();
                for (File file:fileList) {
                    fileInputStream=new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fileInputStream);//存储文件
                }

            } catch (IOException e) {
                logger.error("FTP保存文件错误",e);
                isUpload=false;
            }finally {

                    fileInputStream.close();
                    fileInputStream=null;
                    ftpClient.disconnect();
            }
        }
        return isUpload;
    }

    /**
     * 连接FTP服务器
     * @param ip
     * @param port
     * @param userName
     * @param pwd
     * @return
     */
    private  boolean isConncet2FTPServer(String ip,int port,String userName,String pwd){
        boolean isSuccess=false;
        ftpClient=new FTPClient();
        try {
            ftpClient.connect(ip);//连接
            isSuccess=ftpClient.login(userName,pwd);//登录
        } catch (IOException e) {
            logger.error("连接失败",e);
        }
        return isSuccess;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
