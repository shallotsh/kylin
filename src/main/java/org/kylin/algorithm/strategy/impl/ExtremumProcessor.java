package org.kylin.algorithm.strategy.impl;

import org.kylin.algorithm.strategy.AbstractSequenceProcessor;
import org.kylin.bean.p5.WCode;
import org.kylin.util.WCodeUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ExtremumProcessor extends AbstractSequenceProcessor {

    @Override
    public List<WCode> process(List<WCode> deletedCodes) {
        if(!validate()){
            return this.wCodes;
        }

        List<WCode> ret = wCodes.stream().filter(wCode -> !WCodeUtils.isExtremumCodes(wCode)).collect(Collectors.toList());

        return ret;
    }

}
