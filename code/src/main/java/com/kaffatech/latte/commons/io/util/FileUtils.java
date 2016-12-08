package com.kaffatech.latte.commons.io.util;

import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import java.io.Closeable;
import java.io.IOException;

public abstract class FileUtils extends org.apache.commons.io.FileUtils {

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

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			throw new IoRuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(getFolder("/x/y/z.jpg"));
	}
}
