package org.kylin.algorithm.strategy.impl;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 五码之和大于30者杀
 */
public class BigSumProcessor implements SequenceProcessor {

    private List<WCode> wCodes;

    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
            wCodes = wCodeReq.getWCodes();
        }
        return this;
    }

    @Override
    public List<WCode> process(List<WCode> deletedCodes) {
        if(!validate()){
            return wCodes;
        }
        List<WCode> ret = new ArrayList<>();
        for(WCode wCode : wCodes){
            if(wCode != null && wCode.sum() <= 36){
                ret.add(wCode);
            }else if(deletedCodes != null){
                // 添加到已杀码中
                deletedCodes.add(wCode);
            }
        }
//        wCodes.stream().filter(wCode -> wCode != null && wCode.sum() <= 36).collect(Collectors.toList());

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
