package org.kylin.web;

import com.alibaba.fastjson.JSON;
import org.kylin.algorithm.strategy.impl.IterationStrategy;
import org.kylin.bean.*;
import org.kylin.service.strategy.StrategyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author huangyawu
 * @date 2017/7/29 下午2:31.
 */
@Controller
@RequestMapping("/api/strategy")
public class ApiStrategyProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiStrategyProvider.class);

    @Resource
    private StrategyProvider strategyProvider;


    @ResponseBody
    @RequestMapping(value = "/key",  method = {RequestMethod.POST, RequestMethod.GET})
    public WyfResponse oneKeyPredict(@RequestBody WyfParam wyfParam){
        LOGGER.info("one-key-predict wyfParam={}", JSON.toJSONString(wyfParam));
        try {
            WelfareCode welfareCode = strategyProvider.encode(wyfParam, new IterationStrategy());
            LOGGER.info("one-key-result ret = {}", welfareCode.getCodes());
            return new WyfDataResponse<>(welfareCode);
        } catch (Exception e) {
            return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "一键执行失败");
        }
    }
}
