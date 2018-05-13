package org.kylin.algorithm.strategy.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.bean.W3DCode;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.WCodeUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TailThreeCompareProcessor implements SequenceProcessor {
    private List<WCode> wCodes;
    private List<W3DCode> w3DCodes;


    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
           wCodes = wCodeReq.getWCodes();
           w3DCodes = JSON.parseArray(wCodeReq.getP3Code(), W3DCode.class);
        }
        return this;
    }

    @Override
    public List<WCode> process() {
        if(!validate()){
            return wCodes;
        }

        List<WCode> ret = wCodes.stream().filter(wCode -> WCodeUtils.compareTailThreeBit(w3DCodes, wCode)).collect(Collectors.toList());

        return ret;
    }

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes) || CollectionUtils.isEmpty(w3DCodes)){
            return false;
        }
        return true;
    }
}
