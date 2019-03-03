package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BitFilterStrategy implements Strategy<List<WCode>, WCodeReq> {

    @Override
    public boolean shouldExecute(WCodeReq param) {
        if(!Strategy.super.shouldExecute(param)){
            return false;
        }

        if(!validate(param)){
            return false;
        }

        return true;
    }

    @Override
    public List<WCode> execute(WCodeReq param, List<WCode> wCodes) {
        if(!validate(param)){
            return wCodes;
        }

        List<Set<Integer>> bitsArray = new ArrayList<>();
        for(String bitStr : param.getBits()){
            Set<Integer> set = TransferUtil.toIntegerSet(bitStr);
            bitsArray.add(set);
        }

        // 默认bits序列从低位开始
        int dim = 0;
        for(Set<Integer> bits: bitsArray){
            if(CollectionUtils.isEmpty(bits)){
                dim++;
                continue;
            }
            Iterator<WCode> iterator = wCodes.iterator();
            while (iterator.hasNext()){
                WCode wCode = iterator.next();
                if(dim >= wCode.getDim()){
                    continue;
                }
                if(!bits.contains(wCode.getCodes().get(dim))){
                    iterator.remove();
                }
            }
            dim++;
        }

        return wCodes;
    }

    private boolean validate(WCodeReq wCodeReq){
        if(wCodeReq == null || CollectionUtils.isEmpty(wCodeReq.getBits())
                || CollectionUtils.isEmpty(wCodeReq.getWCodes())){
            return false;
        }

        return true;
    }
}
