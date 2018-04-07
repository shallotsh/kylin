package org.kylin.algorithm.strategy.impl;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 五码之和小于20者杀
 */
public class LitttleSumProcessor implements SequenceProcessor {

    private List<WCode> wCodes;

    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
            wCodes = wCodeReq.getWCodes();
        }
        return this;
    }

    @Override
    public List<WCode> process() {
        if(!validate()){
            return wCodes;
        }
        List<WCode> ret = wCodes.stream().filter(wCode -> wCode != null && wCode.sum() >= 20).collect(Collectors.toList());
        return ret;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes)){
            return false;
        }
        return true;
    }
}
