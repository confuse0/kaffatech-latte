package com.kaffatech.latte.commons.net.ftp.util;

import com.kaffatech.latte.commons.io.util.FileUtils;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.ctx.base.SystemProperties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * @author lingzhen on 16/12/27.
 */
public class FtpUtils {

    private static FTPClient FTP_CLIENT = new FTPClient();

    private static final String DEF_ENCODING = getEncoding();

    public static boolean uploadFile(String url, String username, String password, String remoteFolder, String localPath) {
        return uploadFile(url, DEF_ENCODING, username, password, remoteFolder, localPath);
    }

    public static boolean uploadFile(String url, int port, String username, String password, String remoteFolder, String localPath) {
        return uploadFile(url, port, DEF_ENCODING, username, password, remoteFolder, localPath);
    }

    public static boolean uploadFile(String url, String encoding, String username, String password, String remoteFolder, String localPath) {
        return uploadFile(url, 21, encoding, username, password, remoteFolder, localPath);
    }

    /**
     * 向FTP服务器上传文件
     *
     * @param url          FTP服务器URL
     * @param port         FTP服务器PORT
     * @param encoding     FTP服务器编码
     * @param username     FTP登录账号
     * @param password     FTP登录密码
     * @param remoteFolder FTP服务器文件目录,根目录为'/'
     * @param localPath    本地的文件路径,含文件名
     * @return
     */
    public static boolean uploadFile(String url, int port, String encoding, String username, String password, String remoteFolder, String localPath) {
        boolean result = false;
        InputStream input = null;
        try {
            // 打开本地文件
            input = new FileInputStream(new File(localPath));

            // 连接FTP服务器
            FTP_CLIENT.setControlEncoding(encoding);
            FTP_CLIENT.connect(url, port);

            // 检验是否连接成功
            if (FTPReply.isPositiveCompletion(FTP_CLIENT.getReplyCode())) {
                // 登录
                FTP_CLIENT.login(username, password);

                // 设置文件传输类型为二进制
                FTP_CLIENT.setFileType(FTPClient.BINARY_FILE_TYPE);

                // 转移工作目录至指定目录下
                String fileName = FileUtils.getFileName(localPath);
                if (FTP_CLIENT.changeWorkingDirectory(remoteFolder)) {
                    result = FTP_CLIENT.storeFile(fileName, input);
                }
            } else {
                Log.ERROR_LOGGER.error("上传文件连接失败");
            }
        } catch (IOException e) {
            Log.ERROR_LOGGER.error("FTP文件上传异常:" + e.getMessage(), e);
        } finally {
            try {
                // 关闭文件流
                FileUtils.close(input);
                // 登出
                FTP_CLIENT.logout();
                // 关闭连接
                if (FTP_CLIENT.isConnected()) {
                    FTP_CLIENT.disconnect();
                }
            } catch (IOException ioe) {
                // 忽略关闭异常
            }
        }
        return result;
    }

    public static boolean downloadFile(String url, String username, String password, String remotePath, String localFolder) {
        return downloadFile(url, DEF_ENCODING, username, password, remotePath, localFolder);
    }

    public static boolean downloadFile(String url, int port, String username, String password, String remotePath, String localFolder) {
        return downloadFile(url, port, DEF_ENCODING, username, password, remotePath, localFolder);
    }

    public static boolean downloadFile(String url, String encoding, String username, String password, String remotePath, String localFolder) {
        return downloadFile(url, 21, encoding, username, password, remotePath, localFolder);
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param url          FTP服务器URL
     * @param port         FTP服务器PORT
     * @param encoding     FTP服务器编码
     * @param username     FTP登录账号
     * @param password     FTP登录密码
     * @param remoteFolder FTP服务器文件目录,根目录为'/'
     * @param localPath    本地的文件路径,含文件名
     * @return
     */
    public static boolean downloadFile(String url, int port, String encoding, String username, String password, String remoteFolder, String localPath) {
        boolean result = false;
        OutputStream output = null;
        try {
            // 打开本地文件
            output = new FileOutputStream(new File(localPath));

            // 连接FTP服务器
            FTP_CLIENT.setControlEncoding(encoding);
            FTP_CLIENT.connect(url, port);

            // 检验是否连接成功
            if (FTPReply.isPositiveCompletion(FTP_CLIENT.getReplyCode())) {
                // 登录
                FTP_CLIENT.login(username, password);

                // 设置文件传输类型为二进制
                FTP_CLIENT.setFileType(FTPClient.BINARY_FILE_TYPE);

                // 转移到FTP服务器目录至指定的目录下
                String fileName = FileUtils.getFileName(localPath);
                if (FTP_CLIENT.changeWorkingDirectory(remoteFolder)) {
                    // 获取文件列表
                    FTPFile[] fileArray = FTP_CLIENT.listFiles();
                    for (FTPFile each : fileArray) {
                        if (each.getName().equals(fileName)) {
                            // 找到下载文件进行下载
                            FTP_CLIENT.retrieveFile(fileName, output);
                            break;
                        }
                    }
                }
                result = true;
            } else {
                Log.ERROR_LOGGER.error("连接失败");
            }
        } catch (IOException e) {
            Log.ERROR_LOGGER.error("FTP文件下载异常:" + e.getMessage(), e);
        } finally {
            try {
                // 关闭文件流
                FileUtils.close(output);
                // 登出
                FTP_CLIENT.logout();
                // 关闭连接
                if (FTP_CLIENT.isConnected()) {
                    FTP_CLIENT.disconnect();
                }
            } catch (IOException ioe) {
                // 忽略关闭异常
            }
        }
        return result;
    }

    private static String getEncoding() {
        String ftpEncoding = SystemProperties.getProperty("ftpEncoding");
        if (StringUtils.isEmpty(ftpEncoding)) {
            ftpEncoding = "UTF-8";
        }
        return ftpEncoding;
    }

    public static void main(String[] args) {
    }

}
