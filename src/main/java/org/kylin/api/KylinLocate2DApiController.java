package org.kylin.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfErrorResponse;
import org.kylin.bean.WyfResponse;
import org.kylin.bean.p2.XCodePair;
import org.kylin.bean.p2.XCodeReq;
import org.kylin.bean.p5.WCodeSummarise;
import org.kylin.util.WCodeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/2d")
@Slf4j
public class KylinLocate2DApiController {

    @ResponseBody
    @RequestMapping("/shuffle")
    public WyfResponse shuffle2d(@RequestBody XCodeReq req){

        log.info("shuffle req:{}", req);

        return new WyfDataResponse<>(new WCodeSummarise().setwCodes(WCodeUtils.mockCodes(2, 20)));
    }


    @ResponseBody
    @RequestMapping("/kill/code")
    public WyfResponse killCode(@RequestBody XCodeReq req, HttpServletRequest request){

        log.info("kill req:{}", req);

       return  new WyfDataResponse<>(new WCodeSummarise().setwCodes(Arrays.asList(WCodeUtils.mock(2))));
    }

    @ResponseBody
    @RequestMapping("/comp/select")
    public WyfResponse compSelect(@RequestBody XCodeReq req){
        log.info("comp-select req:{}", req);

        return  new WyfDataResponse<>(new WCodeSummarise().setwCodes(Arrays.asList(WCodeUtils.mock(2))));
    }


    @ResponseBody
    @RequestMapping(value = "/codes/export",  method = RequestMethod.POST)
    public WyfResponse exportCodes(@RequestBody XCodeReq req) {
        if(req == null){
            return new WyfErrorResponse(HttpStatus.BAD_REQUEST.value(), "导出数据错误");
        }

        Optional<String> optFile = Optional.empty();
        if(optFile.isPresent()){
            return new WyfDataResponse<>(optFile.get());
        }else{
            log.error("export-codes-error wCodeReq={}", JSON.toJSONString(req));
            return new WyfErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");
        }
    }


}
