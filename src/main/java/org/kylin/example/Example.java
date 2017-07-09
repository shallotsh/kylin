package org.kylin.example;

import org.kylin.bean.WelfareCode;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfResponse;
import org.kylin.constant.CodeTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyawu
 * @date 2017/6/25 上午11:41.
 */
@Controller
@RequestMapping("/example")
public class Example {
    private static final Logger LOGGER = LoggerFactory.getLogger(Example.class);

    @ResponseBody
    @RequestMapping("/")
    public String home(){

        LOGGER.info("spring-boot-visit-home");
        return "Hello Spring boot.";
    }

    @ResponseBody
    @RequestMapping("/codes")
    public WyfResponse getExampleCodes(){
        WelfareCode welfareCode = new WelfareCode();
        welfareCode.setCodeTypeEnum(CodeTypeEnum.GROUP);
        List<String> codes = new ArrayList<>();
        codes.add("123");
        codes.add("324");
        welfareCode.setCodes(codes);

        return new WyfDataResponse<>(welfareCode);

    }


}
