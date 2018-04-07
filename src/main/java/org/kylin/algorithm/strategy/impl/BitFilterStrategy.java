package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;

import java.util.*;

public class BitFilterStrategy implements Strategy<List<WCode>, WCodeReq> {

    @Override
    public List<WCode> execute(WCodeReq param) {
        if(param == null){
            return Collections.emptyList();
        }
        if(!validate(param)){
            return param.getWCodes();
        }

        List<Set<Integer>> bitsArray = new ArrayList<>();
        for(String bitStr : param.getBits()){
            Set<Integer> set = TransferUtil.toIntegerSet(bitStr);
            bitsArray.add(set);
        }

        List<WCode> wCodes = param.getWCodes();

        // 默认bits序列从低位开始
        int dim = 0;
        for(Set<Integer> bits: bitsArray){
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
