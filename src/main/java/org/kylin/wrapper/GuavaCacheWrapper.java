package org.kylin.wrapper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GuavaCacheWrapper {

    private static Cache<String, Object> cache;

    @PostConstruct
    public void init(){
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .recordStats()
                .build();
        log.info("cache initialized.");
    }


    public<T> void put(String key, T obj){
        assertCache();
        cache.put(key, obj);
    }

    public <T> T  getIfPresent(String key){
        assertCache();
        return (T)cache.getIfPresent(key);
    }

    public void invalidateAll(){
        assertCache();
        cache.invalidateAll();
    }

    public void invalidate(String key){
        assertCache();
        cache.invalidate(key);
    }


    public void assertCache(){
        if(Objects.isNull(cache)){
            throw new RuntimeException("Cache is not be initialized.");
        }
    }

}
