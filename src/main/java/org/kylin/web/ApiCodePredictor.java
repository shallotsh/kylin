package org.kylin.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.*;
import org.kylin.constant.CodeTypeEnum;
import org.kylin.service.WelfareCodePredictor;
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
 * @date 2017/6/25 下午3:30.
 */
@Controller
@RequestMapping("/api/welfare")
public class ApiCodePredictor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiCodePredictor.class);

    @Resource
    private WelfareCodePredictor welfareCodePredictor;

    @ResponseBody
    @RequestMapping(value = "/predictor/codes",  method = {RequestMethod.POST, RequestMethod.GET})
    public WyfResponse predict(@RequestBody WyfParam wyfParam){
        LOGGER.info("api-code-predictor-predict param={}", JSON.toJSONString(wyfParam));

        if(wyfParam == null || CollectionUtils.isEmpty(wyfParam.getRiddles())
                || wyfParam.getTargetCodeType() == null){
            LOGGER.warn("api-code-predictor-predict-bad-quest");
            return new WyfErrorResponse(HttpStatus.BAD_REQUEST.value(), "param invalid.");
        }

        WelfareCode welfareCode = welfareCodePredictor.encode(wyfParam.getRiddles(), CodeTypeEnum.getById(wyfParam.getTargetCodeType()));

        LOGGER.debug("codes:{}", JSON.toJSONString(welfareCode));

        return new WyfDataResponse<>(welfareCode);
    }

    @ResponseBody
    @RequestMapping(value = "/transfer/codes",  method = RequestMethod.POST)
    public WyfResponse transfer(WyfParam wyfParam){

        return new WyfDataResponse<>(null);
    }

    @ResponseBody
    @RequestMapping(value = "/filter/codes", method = RequestMethod.POST)
    public WyfResponse doFilter(@RequestBody WyfParam wyfParam){

        return new WyfDataResponse<>(null);
    }

    @ResponseBody
    @RequestMapping(value = "/select/codes",  method = RequestMethod.POST)
    public WyfResponse select(@RequestBody WyfParam wyfParam){

        return new WyfDataResponse<>(null);
    }

    @ResponseBody
    @RequestMapping(value = "/export/codes",  method = RequestMethod.POST)
    public WyfResponse exportCodes(@RequestBody WyfParam wyfParam){

        return new WyfDataResponse<>(null);
    }
}
