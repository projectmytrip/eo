package com.eni.ioc;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

	private static final Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		String type = cacheEvent.getType() != null ? cacheEvent.getType().toString() : "null";
		String key = cacheEvent.getKey() != null ? cacheEvent.getKey().toString() : "null";
		String oValue = cacheEvent.getOldValue() != null ? cacheEvent.getOldValue().toString() : "null";
		String nValue = cacheEvent.getNewValue() != null ? cacheEvent.getNewValue().toString() : "null";
		logger.info("CACHE-> TYPE:{}, KEY:{}, OLDVALUE:{}, NEWVALUE:{}", type, key, oValue, nValue);
	}
}