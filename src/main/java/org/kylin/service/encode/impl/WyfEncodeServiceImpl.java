package org.kylin.service.encode.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.WelfareCode;
import org.kylin.util.Encoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.kylin.service.encode.WyfEncodeService;

import java.util.*;

/**
 * @author huangyawu
 * @date 2017/6/29 下午11:26.
 */

@Service
public class WyfEncodeServiceImpl implements WyfEncodeService{
    private static final Logger LOGGER = LoggerFactory.getLogger(WyfEncodeServiceImpl.class);



    @Override
    public WelfareCode quibinaryEncode(List<Set<Integer>> riddles) {
        LOGGER.info("quibinary-encode riddles={} ", riddles);

        if(CollectionUtils.size(riddles) < 3){
            LOGGER.warn("quibinary-encode-exception riddles={}", riddles);
            throw new IllegalArgumentException("参数错误");
        }

        return Encoders.quibinaryEncode3DCodes(riddles);
    }

    @Override
    public WelfareCode groupSelectEncode(List<Set<Integer>> riddles) {
        LOGGER.info("group-select-encode riddles={} ", riddles);

        if(CollectionUtils.size(riddles) < 3){
            LOGGER.warn("group-select-exception riddles={}", riddles);
            throw new IllegalArgumentException("参数错误");
        }

        WelfareCode welfareCode = directSelectEncode(riddles);

        return welfareCode.toGroup();
    }

    @Override
    public WelfareCode directSelectEncode(List<Set<Integer>> riddles) {

        LOGGER.info("direct-select-encode riddles={} ", riddles);

        if(CollectionUtils.size(riddles) < 3){
            LOGGER.warn("group-select-exception riddles={}", riddles);
            throw new IllegalArgumentException("参数错误");
        }

        WelfareCode welfareCode = Encoders.directSelectPredict3DCodes(riddles);
        if(welfareCode != null && !CollectionUtils.isEmpty(welfareCode.getW3DCodes())) {
            welfareCode.distinct().sort(WelfareCode::freqSort).generate();
        }

        LOGGER.info("direct-select-codes={}", JSON.toJSONString(welfareCode));
        return welfareCode;
    }

}
