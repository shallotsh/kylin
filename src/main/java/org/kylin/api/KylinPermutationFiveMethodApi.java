package org.kylin.api;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfErrorResponse;
import org.kylin.bean.WyfResponse;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.service.pfive.WCodeProcessService;
import org.kylin.util.DocUtils;
import org.kylin.util.WCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/p5")
public class KylinPermutationFiveMethodApi {
    private static Logger LOGGER = LoggerFactory.getLogger(KylinPermutationFiveMethodApi.class);

    @Resource
    private WCodeProcessService wCodeProcessService;

    @ResponseBody
    @RequestMapping(value = "/permutation/five", method = {RequestMethod.POST, RequestMethod.GET})
    public WyfResponse transferToPermutationFive(@RequestBody String codeString, HttpServletRequest request){
        LOGGER.info("收到排五请求, codeString={}", codeString);
        if(StringUtils.isBlank(codeString)){
            return new WyfErrorResponse(HttpStatus.BAD_REQUEST.value(), "参数为空");
        }

        List<W3DCode> w3DCodes = JSON.parseArray(codeString, W3DCode.class);


        List<WCode> wCodes = WCodeUtils.fromW3DCodes(w3DCodes);

        List<WCode> permutations = WCodeUtils.transferToPermutationFiveCodes(wCodes);

        return new WyfDataResponse<>(permutations);
    }


    @ResponseBody
    @RequestMapping(value = "/sequence/process", method = {RequestMethod.POST, RequestMethod.GET})
    public WyfResponse sequenceProcess(@RequestBody WCodeReq wCodeReq, HttpServletRequest request){
        LOGGER.info("收到串处理: wCodeReq={}", wCodeReq);
        List<WCode> wCodes = wCodeProcessService.sequenceProcess(wCodeReq);
        LOGGER.info("串处理完成: wCodeReq={},wCodeReq={}", wCodeReq, wCodes);
        return new WyfDataResponse<>(wCodes);
    }


    @ResponseBody
    @RequestMapping(value = "/bits/process", method = {RequestMethod.POST, RequestMethod.GET})
    public WyfResponse bitsProcess(@RequestBody WCodeReq wCodeReq){
        LOGGER.info("收到位处理: wCodeReq={}", wCodeReq);
        List<WCode> wCodes = wCodeProcessService.bitsProcess(wCodeReq);
        LOGGER.info("位处理完成: wCodeReq={},wCodeReq={}", wCodeReq, wCodes);
        return new WyfDataResponse<>(wCodes);
    }


    @ResponseBody
    @RequestMapping(value = "/codes/export",  method = RequestMethod.POST)
    public WyfResponse exportCodes(@RequestBody WCodeReq wCodeReq){
        if(wCodeReq == null){
            return new WyfErrorResponse(HttpStatus.BAD_REQUEST.value(), "导出数据错误");
        }

        try {
            String fileName = DocUtils.saveWCodes(wCodeReq);
            return  new WyfDataResponse<>(fileName);
        } catch (IOException e) {
            LOGGER.error("export-codes-error wCodeReq={}", JSON.toJSONString(wCodeReq));
            return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");
        }
    }



}
