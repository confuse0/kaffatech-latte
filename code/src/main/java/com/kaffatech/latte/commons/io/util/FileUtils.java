package com.kaffatech.latte.commons.io.util;

import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import org.elasticsearch.common.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;

public abstract class FileUtils extends org.apache.commons.io.FileUtils {

	public static String getExtension(String path) {
		String[] array = StringUtils.split(path, ".");
		if (array.length > 1) {
			return array[1];
		}
		return null;
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
}
