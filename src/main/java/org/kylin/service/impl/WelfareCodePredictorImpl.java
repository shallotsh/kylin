package org.kylin.service.impl;

import org.kylin.bean.WelfareCode;
import org.kylin.constant.CodeTypeEnum;
import org.kylin.service.WelfareCodePredictor;
import org.kylin.util.TransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.kylin.service.encode.WyfEncodeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/7/2 下午4:13.
 */
@Service
public class WelfareCodePredictorImpl implements WelfareCodePredictor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelfareCodePredictorImpl.class);

    @Resource
    private WyfEncodeService wyfEncodeService;

    @Override
    public WelfareCode encode(List<String> riddles, CodeTypeEnum codeTypeEnum) {
        if(CollectionUtils.isEmpty(riddles) || codeTypeEnum == null){
            LOGGER.info("wyf-encode-service-param-invalid riddles={},codeTypeEnum={}", riddles, codeTypeEnum);
            return null;
        }

        LOGGER.info("wyf-encode-service-start riddles={}, codeTypeEnum={}", riddles, codeTypeEnum);

        List<Set<Integer>> sets = TransferUtil.toIntegerSets(riddles);

        WelfareCode welfareCode = null;

        try {
            switch (codeTypeEnum){
                case QUIBINARY:
                    welfareCode = wyfEncodeService.quibinaryEncode(sets);
                    break;
                case DIRECT:
                    welfareCode = wyfEncodeService.directSelectEncode(sets);
                    break;
                case GROUP:
                    welfareCode = wyfEncodeService.groupSelectEncode(sets);
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("wyf-encode-service-encode-error riddles={}, codeType={}", riddles, codeTypeEnum, e);
        }

        return welfareCode;

    }



}
