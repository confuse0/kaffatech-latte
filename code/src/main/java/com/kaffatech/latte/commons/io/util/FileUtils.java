package com.kaffatech.latte.commons.io.util;

import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class FileUtils extends org.apache.commons.io.FileUtils {

    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * 获取文件扩展名
     *
     * @param path
     * @return
     */
    public static String getExtension(String path) {
        String[] array = StringUtils.split(path, ".");
        if (array.length > 1) {
            return array[1];
        }
        return null;
    }

    /**
     * 获取文件夹路径
     *
     * @param path
     * @return
     */
    public static String getFolder(String path) {
        int idx = path.lastIndexOf(StringUtils.FILE_SEP);
        return path.substring(0, idx + 1);
    }

    /**
     * 获取文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        int idx = path.lastIndexOf(StringUtils.FILE_SEP);
        return path.substring(idx);
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            Log.ERROR_LOGGER.error("关闭失败:" + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        System.out.println(getFolder("/x/y/z.jpg"));
        System.out.println(getFileName("/x/y/z.jpg"));
    }
}
