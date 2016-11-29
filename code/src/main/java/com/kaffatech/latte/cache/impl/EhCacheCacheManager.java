
package com.kaffatech.latte.cache.impl;

import com.kaffatech.latte.cache.Cache;
import com.kaffatech.latte.cache.CacheManager;

/**
 * @author zhen.ling
 *
 */
public class EhCacheCacheManager implements CacheManager {

	private net.sf.ehcache.CacheManager delegate;

	public EhCacheCacheManager(net.sf.ehcache.CacheManager delegate) {
		this.delegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uuban.toffee.cache.CacheManager#getCache(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <K, V> Cache<K, V> getCache(String name) {
		return new EhCacheCache(delegate.getCache(name));
	}

}
