package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.pattern.BitSeqEnum;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.BitsUtils;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class BitSeqFilterStrategy implements Strategy<List<WCode>, WCodeReq> {

    @Override
    public boolean shouldExecute(WCodeReq param) {
        if(param == null || CollectionUtils.isEmpty(param.getBitsSeq()) ||
                CollectionUtils.isEmpty(param.getwCodes())){
            return false;
        }
        return true;
    }

    @Override
    public List<WCode> execute(WCodeReq param, List<WCode> wCodes) {

        if(!shouldExecute(param)){
            return wCodes;
        }

        List<BitSeqEnum> bitSeqEnums = BitsUtils.getBitSeqEnums(param.getBitsSeq());
        if(CollectionUtils.isEmpty(bitSeqEnums)){
            return wCodes;
        }

        Iterator<WCode> iterator = wCodes.iterator();
        while (iterator.hasNext()){
            WCode wCode = iterator.next();

            boolean flag = false;
            for(BitSeqEnum seqEnum : bitSeqEnums){
                if(seqEnum.seqEqual(wCode.getCodes())){
                    flag = true;
                    break;
                }
            }

            if(!flag){
                iterator.remove();
            }
        }

        return wCodes;
    }
}
