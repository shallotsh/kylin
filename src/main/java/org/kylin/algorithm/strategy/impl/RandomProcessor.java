package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.WyfCollectionUtils;

import java.util.List;

public class RandomProcessor implements SequenceProcessor {
    private List<WCode> wCodes;
    private Integer randomCount;


    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
           wCodes = wCodeReq.getWCodes();
           randomCount = NumberUtils.toInt(wCodeReq.getRandomCount(), -1);
        }
        return this;
    }

    @Override
    public List<WCode> process(List<WCode> deletedCodes) {
        if(!validate()){
            return wCodes;
        }

        WyfCollectionUtils.markRandomDeletedByCount(wCodes, randomCount);

        return wCodes;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes) || randomCount <= 0 ){
            return false;
        }
        return true;
    }
}
