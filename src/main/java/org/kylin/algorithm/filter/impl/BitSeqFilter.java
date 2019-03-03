package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.algorithm.pattern.BitSeqEnum;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 按选中三码顺序进行过滤，abc，acb，bac，bca，cab，cba，选中的保留，其他的杀掉，不选不操作
 *
 * a --> hundred bit
 * b --> decade bit
 * c --> unit bit
 *
 */
@Service
public class BitSeqFilter implements CodeFilter<WelfareCode>{

    @Override
    public boolean shouldBeFilter(FilterParam param) {
        if(param == null || param.getAcb() == null ||
                param.getAbc() == null ||
                param.getBac() == null ||
                param.getBca() == null ||
                param.getCab() == null ||
                param.getCba() == null){
            return false;
        }

        if(param.getAbc() == 0 && param.getAcb() == 0 &&
                param.getBac() == 0 && param.getBca() == 0 &&
                param.getCab() == 0 && param.getCba() == 0){
            return false;
        }

        return true;
    }

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) || !shouldBeFilter(filterParam)){
            return;
        }

        List<BitSeqEnum> bitSeqEnums = getBitSeqEnums(filterParam);
        if(CollectionUtils.isEmpty(bitSeqEnums)){
            return;
        }

        List<W3DCode> w3DCodeList = code.getW3DCodes();
        Iterator<W3DCode> iterator = w3DCodeList.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();

            boolean flag = false;
            for(BitSeqEnum seqEnum : bitSeqEnums){
                if(seqEnum.seqEqual(w3DCode.getCodes())){
                    flag = true;
                    break;
                }
            }

            if(!flag){
                iterator.remove();
            }
        }

        code.setW3DCodes(w3DCodeList);
    }

    private List<BitSeqEnum> getBitSeqEnums(FilterParam param){
        List<BitSeqEnum> bitSeqEnums = new ArrayList<>();

        int[] bitSeqIds = new int[]{param.getAbc(), param.getAcb(), param.getBac(), param.getBca(), param.getCab(), param.getCba()};

        for(int id: bitSeqIds){
            Optional<BitSeqEnum> seqEnumOpt = BitSeqEnum.getById(id);
            if(seqEnumOpt.isPresent()){
                bitSeqEnums.add(seqEnumOpt.get());
            }
        }

        return bitSeqEnums;
    }
}
