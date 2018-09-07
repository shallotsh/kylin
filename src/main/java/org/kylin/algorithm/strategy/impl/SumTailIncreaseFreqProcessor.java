package org.kylin.algorithm.strategy.impl;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 胆增频 ，即输入一组胆abcde与现有p5的前三位比较，如前三位中至少有一个与abcde相符，则P5频度加一
 */
public class SumTailIncreaseFreqProcessor implements SequenceProcessor {

    private List<WCode> wCodes;
    private Set<Integer> sumTailCodes;

    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
            wCodes = wCodeReq.getWCodes();
            sumTailCodes = TransferUtil.toIntegerSet(wCodeReq.getBoldCodeFive());
        }

        return this;
    }

    @Override
    public List<WCode> process() {
        if(!validate()){
            return wCodes;
        }

        for(WCode wCode : wCodes){
            int sumTail = wCode.getCodes().subList(0, 3).stream().reduce(0, Integer::sum) % 10;
            if(sumTailCodes.contains(sumTail)){
                wCode.increaseFreq();
            }
        }

        return wCodes;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes) || CollectionUtils.isEmpty(sumTailCodes)){
            return false;
        }
        return true;
    }
}
