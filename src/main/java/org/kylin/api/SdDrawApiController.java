package org.kylin.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfErrorResponse;
import org.kylin.bean.WyfResponse;
import org.kylin.bean.sd.SdDrawNoticeResult;
import org.kylin.util.OkHttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/3d")
@Slf4j
public class SdDrawApiController {

    private static final String DRAW_NOTICE_URL_TPL = "http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name={0}&issueCount={1}";

    @RequestMapping(value = "/draw/notice", method = RequestMethod.GET)
    public WyfResponse findDrawNotice(String name, Integer issueCount){

        if(StringUtils.isBlank(name)) name = "3d";
        if(issueCount == null || issueCount <= 0) issueCount = 1;

        String url = MessageFormat.format(DRAW_NOTICE_URL_TPL, name, issueCount);
        Optional<String> retOpt = OkHttpUtils.doGet(url);
        if(!retOpt.isPresent()){
            return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询开奖结果请求错误");
        }

        try {
            SdDrawNoticeResult result = JSON.parseObject(retOpt.get(), SdDrawNoticeResult.class);
            log.info("开奖结果查询 result:{}", result);
            return new WyfDataResponse<>(result);
        } catch (Exception e) {
            log.info("开奖结果转换错误", e);
        }

        return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询开奖结果错误");
    }

}
