package org.kylin.algorithm.strategy.impl;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.kylin.util.WCodeUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 胆增频 ，即输入一组胆abcde与现有p5的前三位比较，如前三位中至少有一个与abcde相符，则P5频度加一
 */
public class BoldIncreaseFreqProcessor implements SequenceProcessor {

    private List<WCode> wCodes;
    private Set<Integer> boldCodes;

    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
            wCodes = wCodeReq.getWCodes();
            boldCodes = TransferUtil.toIntegerSet(wCodeReq.getBoldCodeFive());
        }

        return this;
    }

    @Override
    public List<WCode> process() {
        if(!validate()){
            return wCodes;
        }

        for(WCode wCode : wCodes){
            if(WCodeUtils.containsBoldCode(wCode, boldCodes)){
                wCode.increaseFreq();
            }
        }

        return wCodes;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes) || CollectionUtils.isEmpty(boldCodes)){
            return false;
        }
        return true;
    }
}
