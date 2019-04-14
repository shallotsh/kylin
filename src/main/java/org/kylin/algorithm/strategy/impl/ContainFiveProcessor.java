package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.kylin.util.WCodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContainFiveProcessor implements SequenceProcessor {
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
    public List<WCode> process(List<WCode> deletedCodes) {
        if(!validate()){
            return wCodes;
        }

        List<WCode> ret = new ArrayList<>();

        for(WCode wCode : wCodes){
            if(WCodeUtils.containInSet(wCode, boldCodes) >= 5){
                ret.add(wCode);
            }else if(deletedCodes != null){
                deletedCodes.add(wCode);
            }
        }

//         wCodes.stream().filter(wCode -> WCodeUtils.containInSet(wCode, boldCodes) >= 5).collect(Collectors.toList());

        return ret;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes) || CollectionUtils.isEmpty(boldCodes)){
            return false;
        }
        return true;
    }
}
