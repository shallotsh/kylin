package org.kylin.algorithm.strategy.impl;

import javafx.util.Pair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.util.TransferUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BitRollFilterStrategy implements Strategy<List<WCode>, WCodeReq> {

    @Override
    public boolean shouldExecute(WCodeReq param) {

        if(Objects.isNull(param) || CollectionUtils.isEmpty(param.getWCodes())){
            return false;
        }

        if(StringUtils.isBlank(param.getBitAB()) && StringUtils.isBlank(param.getBitBC())
                && StringUtils.isBlank(param.getBitCD())
                && StringUtils.isBlank(param.getBitDE())){
            return false;
        }

        return true;
    }

    @Override
    public List<WCode> execute(WCodeReq param, List<WCode> source) {

        List<WCode> ret = new ArrayList<>();
        ret = inverseSelect(source, param.getBitAB(), 0);
        ret = inverseSelect(ret, param.getBitBC(), 1);
        ret = inverseSelect(ret, param.getBitCD(), 2);
        ret = inverseSelect(ret, param.getBitDE(), 3);
        return ret;
    }


    private List<WCode> inverseSelect(List<WCode> source, String bitSeq, int start){
        if(StringUtils.isBlank(bitSeq) || CollectionUtils.isEmpty(source)){
            return source;
        }

        List<Pair<Integer,Integer>> pairs = TransferUtil.parsePairCodeList(bitSeq);

        if(CollectionUtils.isEmpty(pairs)){
            return Collections.emptyList();
        }

        return source.stream()
                .filter(wCode -> !seqEquals(wCode, pairs, start))
                .collect(Collectors.toList());
    }

    private boolean seqEquals(WCode wCode, List<Pair<Integer, Integer>> pairs, int start){

        if(start < 0 || start >= wCode.getCodes().size()-1){
            return false;
        }

        for(Pair<Integer, Integer> pair : pairs){
            if(pair.getKey() == wCode.getCodes().get(start)
                    && pair.getValue() == wCode.getCodes().get(start+1)){
                return true;
            }
        }

        return false;
    }



}
