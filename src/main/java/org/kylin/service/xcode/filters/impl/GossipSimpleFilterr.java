package org.kylin.service.xcode.filters.impl;

import javafx.util.Pair;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.p5.WCode;
import org.kylin.service.xcode.filters.SimpleFilter;
import org.kylin.util.TransferUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GossipSimpleFilterr implements SimpleFilter{

    @Override
    public List<WCode> filter(List<WCode> target, String filterStr) {

        if(CollectionUtils.isEmpty(target)){
            return Collections.emptyList();
        }

        List<Pair<Integer,Integer>> gossips = TransferUtil.parsePairCodeList(filterStr);
        if(CollectionUtils.isEmpty(gossips)){
            return target;
        }

        List<WCode> ret = target.stream().filter(wCode -> isContain(wCode, gossips)).collect(Collectors.toList());

        return ret;
    }

    private boolean isContain(WCode wCode, List<Pair<Integer,Integer>> gossips){
        for(Pair<Integer,Integer> pair : gossips){

            if((wCode.getCodes().get(0) == pair.getKey()
                    || wCode.getCodes().get(1) == pair.getKey())
                    && (wCode.getCodes().get(0) == pair.getValue()
                    || wCode.getCodes().get(1) == pair.getValue())){
                return true;
            }
        }

        return false;
    }
}
