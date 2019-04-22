package org.kylin.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfErrorResponse;
import org.kylin.bean.WyfResponse;
import org.kylin.bean.sd.SdDrawNoticeResult;
import org.kylin.util.OkHttpUtils;
import org.kylin.wrapper.GuavaCacheWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/3d")
@Slf4j
public class SdDrawApiController {



    @Autowired
    private GuavaCacheWrapper cacheWrapper;

    @RequestMapping(value = "/draw/notice", method = RequestMethod.GET)
    public WyfResponse findDrawNotice(String name, Integer issueCount){

        if(StringUtils.isBlank(name)) name = "3d";
        if(issueCount == null || issueCount <= 0) issueCount = 1;

        String key = name + issueCount;

        SdDrawNoticeResult result = cacheWrapper.getIfPresent(key);
        if(!Objects.isNull(result)){
            return new WyfDataResponse<>(result);
        }

        Optional<SdDrawNoticeResult> resultOpt = OkHttpUtils.getSdDrawNoticeResult(name, issueCount);
        if(!resultOpt.isPresent()){
            return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询开奖错误");
        }

        log.info("查询开奖结果 result:{}", resultOpt.get());

        cacheWrapper.put(key, resultOpt.get());

        return new WyfDataResponse<>(resultOpt.get());
    }

}
