package com.kaffatech.latte.commons.net.ftp.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.kaffatech.latte.commons.net.ftp.SftpClient;

import java.util.Properties;

/**
 * @author lingzhen on 17/2/22.
 */
public class SftpUtils {

    /**
     * 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static SftpClient connect(String host, int port, String username,
                               String password) {
        Session sshSession = null;
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SftpClient(sshSession, sftp);
    }
}
