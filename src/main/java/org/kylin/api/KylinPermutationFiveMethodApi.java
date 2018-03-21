package org.kylin.api;

import com.alibaba.fastjson.JSON;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WyfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/p5")
public class KylinPermutationFiveMethodApi {
    private static Logger LOGGER = LoggerFactory.getLogger(KylinPermutationFiveMethodApi.class);

    @ResponseBody
    @RequestMapping(value = "/permutation/five", method = RequestMethod.POST)
    public WyfResponse transferToPermutationFive(String codeString){
        LOGGER.info("收到排五请求, codeString={}", codeString);
        List<W3DCode> w3DCodes = JSON.parseArray(codeString, W3DCode.class);


        return null;
    }


}
