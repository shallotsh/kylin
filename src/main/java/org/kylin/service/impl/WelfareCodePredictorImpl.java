package org.kylin.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.*;
import org.kylin.constant.CodeTypeEnum;
import org.kylin.constant.WelfareConfig;
import org.kylin.service.WelfareCodePredictor;
import org.kylin.util.Encoders;
import org.kylin.util.TransferUtil;
import org.kylin.util.WyfCollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.kylin.service.encode.WyfEncodeService;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/7/2 下午4:13.
 */
@Service
public class WelfareCodePredictorImpl implements WelfareCodePredictor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelfareCodePredictorImpl.class);

    @Resource
    private ApplicationContext applicationContext;

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


    public WelfareCode filter(FilterParam filterParam){
        if(filterParam == null || filterParam.getWelfareCode() == null){
            return null;
        }

        WelfareCode welfareCode = filterParam.getWelfareCode();
        filterParam.setWelfareCode(null);

        if(filterParam.getRandomKilled() != null && filterParam.getRandomKilled() && filterParam.getRandomKillCount() != null && filterParam.getRandomKillCount() > 0){
            LOGGER.info("执行P3随机杀码 count={}", filterParam.getRandomKillCount());
            WyfCollectionUtils.markRandomDeletedByCount(welfareCode.getW3DCodes(), filterParam.getRandomKillCount());
            welfareCode.setRandomKilled(true);
            welfareCode.setNonDeletedPairCount(CollectionUtils.size(TransferUtil.getNonDeletedPairCodes(welfareCode.getW3DCodes())));
            TransferUtil.plusOneFreqs(welfareCode.getW3DCodes());
        }else {
            Map<String, CodeFilter> codeFilterMap = applicationContext.getBeansOfType(CodeFilter.class);
            if (!MapUtils.isEmpty(codeFilterMap)) {
                codeFilterMap.forEach((k, filter) -> welfareCode.filter(filter, filterParam));
            }
        }

        welfareCode.distinct().sort(WelfareCode::freqSort).generate();

        LOGGER.info("filter-end codes={}", welfareCode.getCodes());

        return welfareCode;
    }

    @Override
    public WelfareCode minus(PolyParam polyParam) {
        if(polyParam == null || polyParam.getMinuend() == null || polyParam.getSubtractor() == null){
            throw new IllegalArgumentException("参数错误");
        }

        WelfareCode minuend = polyParam.getMinuend();
        WelfareCode ret = minuend.minus(polyParam.getSubtractor());

        LOGGER.info("minus-set ret={}", JSON.toJSONString(ret));

        return ret;
    }


    @Override
    public WelfareCode compSelect(List<WelfareCode> welfareCodes) {
        if(CollectionUtils.isEmpty(welfareCodes)){
            return null;
        }

        return Encoders.mergeWelfareCodes(welfareCodes);
    }

    @Override
    public WelfareCode highFreq(WelfareCode welfareCode) {
        if(welfareCode == null || CollectionUtils.isEmpty(welfareCode.getW3DCodes())){
            return welfareCode;
        }

        List<W3DCode> w3DCodes = TransferUtil.parseFromStringArrays(WelfareConfig.HFC);
        WelfareCode hfCode = new WelfareCode(welfareCode);
        hfCode.setW3DCodes(w3DCodes);

        List<W3DCode> w3DCodeList = welfareCode.getIntersection(hfCode  );
        welfareCode.setW3DCodes(w3DCodeList);

        welfareCode.sort(WelfareCode::bitSort).generate();

        return welfareCode;
    }

    @Override
    public WelfareCode increaseFreqBySumTail(P3Param p3Param) {
        WelfareCode fCode = p3Param.getWelfareCode();
        if(fCode == null){
            return null;
        }

        String condition = p3Param.getCondition();
        Set<Integer> sumTails = TransferUtil.toIntegerSet(condition);
        if(CollectionUtils.isEmpty(sumTails)){
            return p3Param.getWelfareCode();
        }

        List<W3DCode> w3DCodes = fCode.getW3DCodes();

        // 和值尾增频杀码开始
        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(!sumTails.contains(w3DCode.getSumTail())){
                w3DCode.addFreq(1);
            }
        }

        fCode.setW3DCodes(w3DCodes);
        fCode.sort(WelfareCode::bitSort).generate();

        return fCode;
    }
}
