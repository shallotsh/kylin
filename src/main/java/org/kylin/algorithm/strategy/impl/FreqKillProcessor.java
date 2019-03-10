package org.kylin.algorithm.strategy.impl;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 杀掉指定频度序列的p5
 */
public class FreqKillProcessor implements SequenceProcessor {

    private List<WCode> wCodes;
    private Set<Integer> freqCodes;

    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
            wCodes = wCodeReq.getWCodes();
            freqCodes = TransferUtil.toIntegerSet(wCodeReq.getBoldCodeFive());
        }

        return this;
    }

    @Override
    public List<WCode> process(List<WCode> deletedCodes) {
        if(!validate()){
            return wCodes;
        }

        List<WCode> wCodeList = wCodes.stream()
                .filter(wCode -> !freqCodes.contains(wCode.getFreq()))
                .collect(Collectors.toList());

        return wCodeList;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes) || CollectionUtils.isEmpty(freqCodes)){
            return false;
        }
        return true;
    }
}
