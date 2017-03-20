package com.kaffatech.latte.commons.mail.util;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

/**
 * @author lingzhen on 17/2/10.
 */
public class MailUtils {

    /**
     * 发送邮件
     *
     * @param hostname   主机地址
     * @param from       发件箱
     * @param fromPwd    发件箱密码
     * @param toInfo     收件箱(可多个;分隔)
     * @param subject    标题
     * @param msg        正文
     */
    public static void send(String hostname, String from, String fromPwd, String toInfo, String subject, String msg) {
        SimpleEmail email = new SimpleEmail();
        // email.setDebug(true);
        email.setHostName(hostname);
        email.setAuthentication(from, fromPwd);
        email.setCharset("UTF-8");

        try {
            email.setFrom(from);

            String[] toArray = StringUtils.split(toInfo, ";");
            for (String to : toArray) {
                email.addTo(to);
            }

            email.setSubject(subject);
            email.setMsg(msg);

            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     *
     * @param hostname   主机地址
     * @param from       发件箱
     * @param fromPwd    发件箱密码
     * @param toInfo     收件箱(可多个;分隔)
     * @param subject    标题
     * @param msg        正文
     * @param attachment 附件(可多个;分隔,文件名:文件路径,无文件名则取实际文件名)
     */
    public static void sendWithAttachment(String hostname, String from, String fromPwd, String toInfo, String subject, String msg, String attachment) {
        if (StringUtils.isEmpty(attachment)) {
            throw new IllegalArgumentException("attachment is empty!");
        }

        MultiPartEmail email = new MultiPartEmail();
        // email.setDebug(true);
        email.setHostName(hostname);
        email.setAuthentication(from, fromPwd);
        email.setCharset("UTF-8");

        try {
            email.setFrom(from);

            String[] toArray = StringUtils.split(toInfo, ";");
            for (String to : toArray) {
                email.addTo(to);
            }

            email.setSubject(subject);
            email.setMsg(msg);

            String[] attachArray = StringUtils.split(attachment, ";");
            for (String each : attachArray) {
//                String[] attach = StringUtils.split(each, "]:");
                EmailAttachment emailAttachment = new EmailAttachment();
//                if (attach.length > 1) {
//                    // 包含名字
//                    emailAttachment.setName(MimeUtility.encodeText(attach[0]));
//                    emailAttachment.setPath(attach[1]);
//                } else {
                    String[] filePathArray = StringUtils.split(each, "/");
                    emailAttachment.setName(MimeUtility.encodeText(filePathArray[filePathArray.length - 1]));
                    emailAttachment.setPath(each);
//                }
                email.attach(emailAttachment);
            }

            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
