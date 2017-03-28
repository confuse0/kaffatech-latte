package com.kaffatech.latte.commons.net.ftp.util;

import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.io.util.FileUtils;
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

    private static final String DEF_ENCODING = getEncoding();

    private static final int DEF_PORT = 21;

    public static boolean uploadFile(String url, String username, String password, String remoteFolder, String localPath) {
        return uploadFile(url, DEF_ENCODING, username, password, remoteFolder, localPath);
    }

    public static boolean uploadFile(String url, int port, String username, String password, String remoteFolder, String localPath) {
        return uploadFile(url, port, DEF_ENCODING, username, password, remoteFolder, localPath);
    }

    public static boolean uploadFile(String url, String encoding, String username, String password, String remoteFolder, String localPath) {
        return uploadFile(url, DEF_PORT, encoding, username, password, remoteFolder, localPath);
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
        FTPClient client = null;
        try {
            // 打开本地文件
            input = new FileInputStream(new File(localPath));

            // 连接FTP服务器
            client = connect(url, port, encoding);

            // 检验是否连接成功
            if (FTPReply.isPositiveCompletion(client.getReplyCode())) {
                // 登录
                client.login(username, password);

                // 设置文件传输类型为二进制
                client.setFileType(FTPClient.BINARY_FILE_TYPE);

                // 转移工作目录至指定目录下
                String fileName = FileUtils.getFileName(localPath);
                if (client.changeWorkingDirectory(remoteFolder)) {
                    result = client.storeFile(fileName, input);
                }
            } else {
                throw new IoRuntimeException("FTP文件上传连接失败");
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            close(input, client);
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
        return downloadFile(url, DEF_PORT, encoding, username, password, remotePath, localFolder);
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
        FTPClient client = null;
        try {
            // 打开本地文件
            output = new FileOutputStream(new File(localPath));

            // 连接FTP服务器
            client = connect(url, port, encoding);

            // 检验是否连接成功
            if (FTPReply.isPositiveCompletion(client.getReplyCode())) {
                // 登录
                client.login(username, password);

                // 设置文件传输类型为二进制
                client.setFileType(FTPClient.BINARY_FILE_TYPE);

                // 转移到FTP服务器目录至指定的目录下
                String fileName = FileUtils.getFileName(localPath);
                if (client.changeWorkingDirectory(remoteFolder)) {
                    // 获取文件列表
                    FTPFile[] fileArray = client.listFiles();
                    for (FTPFile each : fileArray) {
                        if (each.getName().equals(fileName)) {
                            // 找到下载文件进行下载
                            client.retrieveFile(fileName, output);
                            break;
                        }
                    }
                }
                result = true;
            } else {
                throw new IoRuntimeException("FTP文件下载连接失败");
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            close(output, client);
        }
        return result;
    }

    private static FTPClient connect(String url, int port, String encoding) throws IOException {
        FTPClient client = new FTPClient();
        client.setControlEncoding(encoding);
        client.connect(url, port);
        return client;
    }

    private static void close(Closeable closeable, FTPClient client) {
        try {
            // 关闭文件流
            FileUtils.close(closeable);

            // 登出
            if (client != null) {
                client.logout();
            }

            // 关闭连接
            if (client != null && client.isConnected()) {
                client.disconnect();
            }
        } catch (IOException ioe) {
            // 忽略关闭异常
        }
    }

    private static String getEncoding() {
        String ftpEncoding = SystemProperties.getProperty("ftpEncoding");
        if (StringUtils.isEmpty(ftpEncoding)) {
            ftpEncoding = "UTF-8";
        }
        return ftpEncoding;
    }

}
