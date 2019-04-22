package org.kylin.task;

import lombok.extern.slf4j.Slf4j;
import org.kylin.bean.sd.SdDrawNoticeResult;
import org.kylin.util.OkHttpUtils;
import org.kylin.wrapper.GuavaCacheWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CacheUpdateTask {

    @Autowired
    private GuavaCacheWrapper cacheWrapper;

    @Scheduled(cron = "0/10 0-50 11 * * ?")
    public void updateTask(){

        log.info("更新缓存");

        Optional<SdDrawNoticeResult> retOpt = OkHttpUtils.getSdDrawNoticeResult("3d", 1);
        if(!retOpt.isPresent()){
            return;
        }

        String key = "3d1";
        cacheWrapper.invalidate(key);
        cacheWrapper.put(key, retOpt.get());

        log.info("更新缓存完成");
    }

}
