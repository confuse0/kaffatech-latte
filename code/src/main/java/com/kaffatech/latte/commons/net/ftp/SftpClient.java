package com.kaffatech.latte.commons.net.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.io.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author lingzhen on 17/2/22.
 */
public class SftpClient {

    /**
     * SSH Session
     */
    private Session sshSession;

    /**
     * SFTP客户端
     */
    private ChannelSftp sftp;

    public SftpClient(Session sshSession, ChannelSftp sftp) {
        this.sshSession = sshSession;
        this.sftp = sftp;
    }

    /**
     * 上传文件
     *
     * @param remoteFolder 文件服务器的上传目录
     * @param localPath    本地待上传文件
     */
    public void upload(String remoteFolder, String localPath) {
        try {
            File file = new File(localPath);
            sftp.cd(remoteFolder);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 下载文件
     *
     * @param remotePath 文件服务器路径
     * @param localPath  本地下载路径
     */
    public void download(String remotePath, String localPath) {
        try {
            File file = new File(localPath);
            sftp.cd(FileUtils.getFolder(remotePath));
            sftp.get(FileUtils.getFileName(remotePath), new FileOutputStream(file));
        } catch (Exception e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 删除文件
     *
     * @param remotePath 要删除文件所在目录
     */
    public void delete(String remotePath) {
        try {
            sftp.cd(FileUtils.getFolder(remotePath));
            sftp.rm(FileUtils.getFileName(remotePath));
        } catch (Exception e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param remoteFolder 文件服务器的目录
     * @return
     */
    public List<ChannelSftp.LsEntry> listFiles(String remoteFolder) {
        try {
            return sftp.ls(remoteFolder);
        } catch (Exception e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 关闭客户端
     */
    public void close() {
        sftp.disconnect();
        sftp.quit();

        sshSession.disconnect();
    }

}
