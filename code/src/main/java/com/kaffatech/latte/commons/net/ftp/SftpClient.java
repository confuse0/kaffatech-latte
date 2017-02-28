package com.kaffatech.latte.commons.net.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.io.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * @author lingzhen on 17/2/22.
 */
public class SftpClient {

    private static String TMP_POSTFIX = "_tmp";

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

    public void upload(String remoteFolder, String localPath) {
        FileInputStream fis = null;
        try {
            sftp.cd(remoteFolder);

            File f = new File(localPath);
            fis = new FileInputStream(f);

            // 上传到临时文件中
            sftp.put(fis, f.getName() + TMP_POSTFIX);
            // 上传完毕更换文件名
            sftp.rename(f.getName() + TMP_POSTFIX, f.getName());
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } catch (SftpException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(fis);
        }
    }

    public void download(String remotePath, String localPath) {
        String remoteFileName = FileUtils.getFileName(remotePath);
        File f = new File(localPath);
        if (f.exists()) {
            throw new IllegalStateException("file already exists!");
        }

        FileOutputStream tempDownloadFos = null;
        try {
            sftp.cd(FileUtils.getFolder(remotePath));
            Vector v = sftp.ls(remoteFileName);
            if (v.size() <= 0) {
                // 文件不存在
                throw new IllegalStateException("file does not exist!");
            }

            File tmpFile = new File(localPath + TMP_POSTFIX);
            if (tmpFile.exists()) {
                // 临时文件已经存在,需要先删除原来的文件
                if (!tmpFile.delete()) {
                    throw new IllegalStateException("temp file can not be deleted!");
                }
            }
            if (!tmpFile.createNewFile()) {
                throw new IllegalStateException("temp file can not be created!");
            }

            // 下载到临时文件中
            tempDownloadFos = new FileOutputStream(tmpFile);
            sftp.get(remoteFileName, tempDownloadFos);

            // 下载完毕更换文件名
            tmpFile.renameTo(f);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } catch (SftpException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(tempDownloadFos);
        }
    }

    public void delete(String remotePath) {
        try {
            sftp.cd(FileUtils.getFolder(remotePath));
            sftp.rm(FileUtils.getFileName(remotePath));
        } catch (SftpException e) {
            throw new IoRuntimeException(e);
        }
    }

    public List<ChannelSftp.LsEntry> listFiles(String remoteFolder) {
        try {
            return sftp.ls(remoteFolder);
        } catch (SftpException e) {
            throw new IoRuntimeException(e);
        }
    }

    public void close() {
        sftp.disconnect();
        sftp.quit();
        sshSession.disconnect();
    }

}
