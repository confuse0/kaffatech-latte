
package com.kaffatech.latte.cache;

/**
 * @author zhen.ling
 *
 */
public interface Cache<K, V> {

	public static final String DEF_KEY = "defKey";

	V get(K key);
	
	void put(K key, V value);
	
	
}
