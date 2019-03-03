package org.kylin.service.pfive.impl;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.constant.FilterStrategyEnum;
import org.kylin.factory.StrategyFactory;
import org.kylin.service.pfive.WCodeProcessService;
import org.kylin.util.WCodeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class WCodeProcessServiceImpl implements WCodeProcessService{


    @Resource
    private List<Strategy< List<WCode>, WCodeReq>> bitStrategies;

    @Override
    public List<WCode> sequenceProcess(WCodeReq wCodeReq) {
        if(wCodeReq == null){
            return Collections.emptyList();
        }
        FilterStrategyEnum filterStrategyEnum = FilterStrategyEnum.getById(wCodeReq.getFilterType());
        if(filterStrategyEnum == null){
            return Collections.emptyList();
        }

        SequenceProcessor sequenceProcessor = StrategyFactory.createProcessor(filterStrategyEnum);
        if(sequenceProcessor == null){
            return Collections.emptyList();
        }
        sequenceProcessor.init(wCodeReq);
        List<WCode> wCodes = sequenceProcessor.process();

        Collections.sort(wCodes);

        if(filterStrategyEnum == FilterStrategyEnum.RANDOM_FILTER){
            WCodeUtils.plusFreq(wCodes);
        }

        return wCodes;
    }


    @Override
    public List<WCode> bitsProcess(WCodeReq wCodeReq) {
        if(wCodeReq == null){
            return Collections.emptyList();
        }
//        Strategy< List<WCode>, WCodeReq> strategy = StrategyFactory.constructBitFilterStrategy();

        List<WCode> wCodes = wCodeReq.getWCodes();
        for(Strategy< List<WCode>, WCodeReq> strategy: bitStrategies) {
            if(strategy.shouldExecute(wCodeReq)) {
                wCodes = strategy.execute(wCodeReq, wCodes);
            }
        }

        Collections.sort(wCodes);

        return wCodes;
    }
}
