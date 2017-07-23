package org.kylin.bean;


import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.constant.CodeTypeEnum;
import org.kylin.util.Encoders;
import org.kylin.util.TransferUtil;

import java.io.Serializable;
import java.util.*;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:14.
 */
public class WelfareCode implements Serializable{
    private Integer codeTypeId;
    private CodeTypeEnum codeTypeEnum;

    private List<W3DCode> w3DCodes;
    private List<String> codes;

    public CodeTypeEnum getCodeTypeEnum() {
        return codeTypeEnum;
    }

    public void setCodeTypeEnum(CodeTypeEnum codeTypeEnum) {
        this.codeTypeEnum = codeTypeEnum;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Integer getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(Integer codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public List<W3DCode> getW3DCodes() {
        return w3DCodes;
    }

    public void setW3DCodes(List<W3DCode> w3DCodes) {
        this.w3DCodes = w3DCodes;
    }

    public WelfareCode distinct(){
        if( CollectionUtils.size(this.getW3DCodes()) <= 1){
            return this;
        }

        List<W3DCode> w3DCodes = this.getW3DCodes();

        // 转成字典
        Map<W3DCode, Integer> w3DCodeIntegerMap = new HashMap<>();
        for(W3DCode w3DCode: w3DCodes){
            if(w3DCodeIntegerMap.containsKey(w3DCode)){
                w3DCodeIntegerMap.put(w3DCode, w3DCodeIntegerMap.get(w3DCode) + 1);
            }else {
                w3DCodeIntegerMap.put(w3DCode, 1);
            }
        }
        List<W3DCode> result = new ArrayList<>();
        w3DCodeIntegerMap.forEach((w3DCode, freq) -> {
            w3DCode.setFreq(freq);
            result.add(w3DCode);
        });

        this.setW3DCodes(result);
        return this;
    }

    public WelfareCode generate(){
        if(CollectionUtils.isEmpty(this.getW3DCodes())){
            return this;
        }
        if(this.getCodeTypeEnum() != null) {
            this.setCodeTypeId(this.getCodeTypeEnum().getId());
        }
        List<W3DCode> w3DCodes = this.getW3DCodes();

        List<String> w3DStrings = new ArrayList<>();
        for(W3DCode w3DCode : w3DCodes){
            w3DStrings.add(w3DCode.toString());
        }

        this.setCodes(w3DStrings);

        return this;
    }

    public WelfareCode toGroup(){
        if(!CodeTypeEnum.DIRECT.equals(codeTypeEnum)
                || CollectionUtils.size(this.getW3DCodes()) <= 1){
            return this;
        }
        // todo
        List<W3DCode> w3DCodes = new ArrayList<>();
        this.getW3DCodes().forEach(w3DCode -> {
            int index = TransferUtil.findInGroupW3DCodes(w3DCodes, w3DCode);

            if(index >= 0){
                w3DCodes.get(index).addFreq(w3DCode.getFreq());
            }else{
                w3DCodes.add(w3DCode);
            }
        });

        this.setW3DCodes(w3DCodes);
        this.asc().distinct().cleanFreq().sort(WelfareCode::bitSort);
        this.codeTypeEnum = CodeTypeEnum.GROUP;
        return this;
    }


    public WelfareCode toDirect(){
        if(!CodeTypeEnum.GROUP.equals(codeTypeEnum)
                || CollectionUtils.size(this.getW3DCodes()) <= 1) {
            return this;
        }
        // todo
        List<W3DCode> w3DCodes = new ArrayList<>();
        this.getW3DCodes().forEach(w3DCode -> {
            int index = TransferUtil.findInDirectW3DCodes(w3DCodes, w3DCode);

            if(index < 0){
                w3DCodes.addAll(Encoders.permutation(w3DCode));
            }
        });

        this.setW3DCodes(w3DCodes);
        this.distinct().cleanFreq().sort(WelfareCode::bitSort);
        this.codeTypeEnum = CodeTypeEnum.DIRECT;
        return this;
    }

    public WelfareCode cleanFreq(){
        if(CollectionUtils.isEmpty(this.getW3DCodes())){
            return this;
        }

        this.getW3DCodes().forEach(w3DCode -> w3DCode.setFreq(1));
        return this;
    }

    public WelfareCode asc(){
        if(CollectionUtils.isEmpty(this.getW3DCodes())){
            return this;
        }

//        this.getW3DCodes().forEach(w3DCode -> w3DCode.asc());
        for(W3DCode w3DCode : this.getW3DCodes()){
            w3DCode.asc();
        }
        return this;
    }

    public WelfareCode filter(CodeFilter<? super WelfareCode> codeFilter, FilterParam filterParam){
        codeFilter.filter(this, filterParam);
        return this;
    }
    
    public WelfareCode sort(Comparator<? super W3DCode> c){
        if(CollectionUtils.isEmpty(this.getW3DCodes())){
            return this;
        }

        this.getW3DCodes().sort(c);

        return this;
    }

    public static int bitSort(W3DCode o1, W3DCode o2){
        if(o1.getCodes()[2] != null && o2.getCodes()[2] != null && !o1.getCodes()[2].equals(o2.getCodes()[2])){
            return o1.getCodes()[2].compareTo(o2.getCodes()[2]);
        }else if (!o1.getCodes()[1].equals(o2.getCodes()[1])){
            return o1.getCodes()[1].compareTo(o2.getCodes()[1]);
        }else{
            return o1.getCodes()[0].compareTo(o2.getCodes()[0]);
        }
    }


    public static int freqSort(W3DCode o1, W3DCode o2){
        if(o1 == null || o2 == null || o1.getFreq() == null || o2.getFreq() == null){
            return 0;
        }

        return o1.getFreq().compareTo(o2.getFreq());
    }

    public static int tailSort(W3DCode o1, W3DCode o2){
        if(o1 == null || o2 == null || o1.getSumTail() == null || o2.getSumTail() == null){
            return 0;
        }

        return o1.getSumTail().compareTo(o2.getSumTail());
    }

}
