package org.kylin.web;

import org.kylin.bean.WelfareCode;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfParam;
import org.kylin.bean.WyfResponse;
import org.kylin.constant.CodeTypeEnum;
import org.kylin.service.WelfareCodePredictor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public WyfResponse predict(String seq1, String seq2, String seq3, String seq4, Integer type){
//        LOGGER.info("api-code-predictor-predict param={}", JSON.toJSONString(wyfParam));

        LOGGER.info("api-code-predictor seq1={}, seq2={}, seq3={}, seq4={}", seq1, seq2, seq3, seq4);
        List<String> seqs = new ArrayList<>();
        seqs.add(seq1);
        seqs.add(seq2);
        seqs.add(seq3);
        seqs.add(seq4);

        WelfareCode welfareCode = welfareCodePredictor.encode(seqs, CodeTypeEnum.getById(type));

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
