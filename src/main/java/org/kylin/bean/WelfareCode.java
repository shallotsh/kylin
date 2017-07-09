package org.kylin.bean;


import org.apache.commons.collections4.CollectionUtils;
import org.kylin.constant.CodeTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
