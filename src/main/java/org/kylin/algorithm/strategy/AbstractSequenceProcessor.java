package org.kylin.algorithm.strategy;

import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.springframework.util.CollectionUtils;

import java.util.List;

abstract public class AbstractSequenceProcessor implements SequenceProcessor{
    protected List<WCode> wCodes;

    @Override
    public SequenceProcessor init(WCodeReq wCodeReq) {
        if(wCodeReq != null){
            wCodes = wCodeReq.getWCodes();
        }
        return this;
    }

    @Override
    abstract public List<WCode> process();

    @Override
    public boolean validate() {
        if(CollectionUtils.isEmpty(wCodes)){
            return false;
        }
        return true;
    }
}
