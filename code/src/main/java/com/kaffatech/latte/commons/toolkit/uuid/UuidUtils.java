
package com.kaffatech.latte.commons.toolkit.uuid;

import java.util.UUID;

/**
 * @author zhen.ling
 *
 */
public class UuidUtils {

	/**
	 * 产生UUID
	 * @return
	 */
	public static String generate() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(generate());
	}
}
