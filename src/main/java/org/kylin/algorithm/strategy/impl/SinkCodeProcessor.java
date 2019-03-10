package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.kylin.util.WCodeUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 下沉比对：即与上次开奖号码比对，如在万千百十个位至少有一个数字下沉，则保留之
 # 比如前天开奖号18079，昨天开奖号为82002，百位下沉
 */
public class SinkCodeProcessor implements SequenceProcessor {
    private List<WCode> wCodes;
    private List<Integer> boldCodes;


    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
           wCodes = wCodeReq.getWCodes();
           boldCodes = TransferUtil.toIntegerList(wCodeReq.getBoldCodeFive());
        }
        return this;
    }

    @Override
    public List<WCode> process(List<WCode> deletedCodes) {
        if(!validate()){
            return wCodes;
        }

        List<WCode> ret = wCodes.stream().filter(wCode -> WCodeUtils.isInHistoryotteryAtLeastOneBit(wCode, boldCodes)).collect(Collectors.toList());

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
