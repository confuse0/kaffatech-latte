
package com.kaffatech.latte.cache;

/**
 * @author zhen.ling
 *
 */
public interface CacheManager {

	<K, V> Cache<K, V> getCache(String name);

}
