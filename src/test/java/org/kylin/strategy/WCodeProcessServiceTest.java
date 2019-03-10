package org.kylin.strategy;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.bean.p5.WCodeSummarise;
import org.kylin.constant.FilterStrategyEnum;
import org.kylin.service.pfive.impl.WCodeProcessServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WCodeProcessServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(WCodeProcessServiceTest.class);

    @Test
    public void testLittleSumProcessor(){
        WCodeReq wCodeReq = new WCodeReq();
        WCode wCode = new WCode(5, 3,8,9,6,4);
        List<WCode> wCodeList = new ArrayList<>();
        wCodeList.add(wCode);
        wCode = new WCode(5, 6,8,9,6,7);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,7,4);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,2,4);
        wCodeList.add(wCode);
        wCodeReq.setWCodes(wCodeList);

        wCodeReq.setFilterType(FilterStrategyEnum.LITTLE_SUM_FILTER.getId());

        Optional<WCodeSummarise> optSms = new WCodeProcessServiceImpl().sequenceProcess(wCodeReq);
        LOGGER.info("wCodes={}", JSON.toJSONString(optSms.get().getwCodes()));
    }


    @Test
    public void testBigSumProcessor(){
        WCodeReq wCodeReq = new WCodeReq();
        WCode wCode = new WCode(5, 3,8,9,6,4);
        List<WCode> wCodeList = new ArrayList<>();
        wCodeList.add(wCode);
        wCode = new WCode(5, 6,8,9,6,7);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,7,4);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,2,4);
        wCodeList.add(wCode);
        wCodeReq.setWCodes(wCodeList);

        wCodeReq.setFilterType(FilterStrategyEnum.BIG_SUM_FILTER.getId());

        Optional<WCodeSummarise> optSms = new WCodeProcessServiceImpl().sequenceProcess(wCodeReq);
        LOGGER.info("wCodes={}", JSON.toJSONString(optSms.get().getwCodes()));
    }


    @Test
    public void testOddEvenProcessor(){
        WCodeReq wCodeReq = new WCodeReq();
        WCode wCode = new WCode(5, 3,8,9,6,4);
        List<WCode> wCodeList = new ArrayList<>();
        wCodeList.add(wCode);
        wCode = new WCode(5, 6,8,9,6,7);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,7,4);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,2,4);
        wCodeList.add(wCode);
        wCodeReq.setWCodes(wCodeList);

        wCodeReq.setFilterType(FilterStrategyEnum.ODD_EVEN_FILTER.getId());

        Optional<WCodeSummarise> optSms = new WCodeProcessServiceImpl().sequenceProcess(wCodeReq);
        LOGGER.info("wCodes={}", JSON.toJSONString(optSms.get().getwCodes()));
    }


    @Test
    public void testContainThreeProcessor(){
        WCodeReq wCodeReq = new WCodeReq();
        WCode wCode = new WCode(5, 3,8,9,6,4);
        List<WCode> wCodeList = new ArrayList<>();
        wCodeList.add(wCode);
        wCode = new WCode(5, 6,8,9,6,7);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,7,3);
        wCodeList.add(wCode);
        wCode = new WCode(5, 1,2,3,2,4);
        wCodeList.add(wCode);
        wCodeReq.setWCodes(wCodeList);
        wCodeReq.setBoldCodeFive("23");

        wCodeReq.setFilterType(FilterStrategyEnum.CONTAIN_THREE_FILTER.getId());

        Optional<WCodeSummarise> optSms = new WCodeProcessServiceImpl().sequenceProcess(wCodeReq);
        LOGGER.info("wCodes={}", JSON.toJSONString(optSms.get().getwCodes()));
    }

}
