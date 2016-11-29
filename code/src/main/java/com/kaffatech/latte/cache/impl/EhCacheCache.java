
package com.kaffatech.latte.cache.impl;

import com.kaffatech.latte.cache.Cache;
import net.sf.ehcache.Element;

/**
 * @author zhen.ling
 *
 */
public class EhCacheCache<K, V>
		implements Cache<K, V> {

	private net.sf.ehcache.Cache delegate;

	public EhCacheCache(net.sf.ehcache.Cache delegate) {
		this.delegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uuban.toffee.cache.Cache#get(java.io.Serializable)
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public V get(K key) {
		Element ele = delegate.get(key);
		if (ele == null) {
			return null;
		}
		return (V) ele.getObjectValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uuban.toffee.cache.Cache#put(java.io.Serializable,
	 * java.io.Serializable)
	 */
	@Override
	public void put(K key, V value) {
		delegate.put(new Element(key, value));
	}

}
