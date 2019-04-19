package org.kylin.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfErrorResponse;
import org.kylin.bean.WyfResponse;
import org.kylin.bean.p2.XCodeReq;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeSummarise;
import org.kylin.service.xcode.XCodeService;
import org.kylin.util.TransferUtil;
import org.kylin.util.WCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/api/2d")
@Slf4j
public class KylinLocate2DApiController {

    @Autowired
    private XCodeService xCodeService;



    @ResponseBody
    @RequestMapping("/shuffle")
    public WyfResponse shuffle2d(@RequestBody XCodeReq req){

        log.info("shuffle req:{}", req);

        if(Objects.isNull(req) || CollectionUtils.isEmpty(req.getSequences())){
            log.warn(" 参数错误");
            return WyfErrorResponse.buildErrorResponse();
        }

        List<Set<Integer>> riddles = TransferUtil.toIntegerSets(req.getSequences());

        List<WCode> ret = xCodeService.quibinaryEncode(riddles);

        log.info("shuffle ret: {}", ret);

        return new WyfDataResponse<>(new WCodeSummarise().setwCodes(ret));
    }


    @ResponseBody
    @RequestMapping("/kill/code")
    public WyfResponse killCode(@RequestBody XCodeReq req, HttpServletRequest request){

        log.info("kill req:{}", req);

        if(Objects.isNull(req)){
            log.warn(" 参数错误");
            return WyfErrorResponse.buildErrorResponse();
        }

        List<WCode> ret = xCodeService.killCodes(req);

        log.info("kill code ret: {}", ret);

       return  new WyfDataResponse<>(new WCodeSummarise().setwCodes(ret));
    }

    @ResponseBody
    @RequestMapping("/comp/select")
    public WyfResponse compSelect(@RequestBody XCodeReq req){
        log.info("comp-select req:{}", req);

        return  new WyfDataResponse<>(new WCodeSummarise().setwCodes(xCodeService.compSelectCodes(req)).setFreqSeted(true));
    }


    @ResponseBody
    @RequestMapping(value = "/codes/export",  method = RequestMethod.POST)
    public WyfResponse exportCodes(@RequestBody XCodeReq req) {
        if(req == null){
            return new WyfErrorResponse(HttpStatus.BAD_REQUEST.value(), "导出数据错误");
        }

        Optional<String> optFile = xCodeService.exportWCodeToFile(req);
        if(optFile.isPresent()){
            return new WyfDataResponse<>(optFile.get());
        }else{
            log.error("export-codes-error wCodeReq={}", JSON.toJSONString(req));
            return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");
        }
    }


}
