package org.kylin.util;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.constant.CodeTypeEnum;

import java.util.*;

/**
 * @author huangyawu
 * @date 2017/7/20 下午11:48.
 */
public class Encoders {
    /**
     * 直选编码
     * @param riddles
     * @return
     */
    public static WelfareCode directSelectPredict3DCodes(List<Set<Integer>> riddles){
        if(CollectionUtils.size(riddles) < 3){
            return null;
        }

        WelfareCode welfareCode = new WelfareCode();
        welfareCode.setCodeTypeEnum(CodeTypeEnum.DIRECT);

        List<W3DCode> w3DCodes = new ArrayList<>();

        for(int i=0; i<riddles.size(); i++){
            for(Integer e1: riddles.get(i)){
                for(int j=0; j<riddles.size(); j++){
                    if(j == i){
                        continue;
                    }
                    for(Integer e2:riddles.get(j)){
                        for(int k=0; k<riddles.size(); k++){
                            if(j == k || i == k){
                                continue;
                            }
                            for(Integer e3: riddles.get(k)){
                                W3DCode w3DCode = new W3DCode();
                                w3DCode.setCodes(new Integer[]{e1, e2, e3});
                                w3DCode.setFreq(1);
                                w3DCode.setSumTail((e1 + e2 + e3) % 10);
                                w3DCodes.add(w3DCode);
                            }

                        }
                    }
                }
            }
        }
        welfareCode.setW3DCodes(w3DCodes);
        return welfareCode;
    }


    /**
     * 二码编码法, 返回组选编码
     * @param riddles
     * @return
     */
    public static WelfareCode quibinaryEncode3DCodes(List<Set<Integer>> riddles){
        if(CollectionUtils.size(riddles) < 3){
            return null;
        }

        WelfareCode welfareCode = new WelfareCode();
        welfareCode.setCodeTypeEnum(CodeTypeEnum.DIRECT);
        welfareCode.setCodeTypeId(CodeTypeEnum.DIRECT.getId());

        List<W3DCode> w3DCodes = new ArrayList<>();

        for(int i=0; i<riddles.size(); i++){
            for(Integer e1: riddles.get(i)){
                for(int j=0; j<riddles.size(); j++){
                    if(j == i){
                        continue;
                    }
                    for (Integer e2: riddles.get(j)) {
                        for (int k = 0; k <= 9; k++) {
                            W3DCode w3DCode = new W3DCode();
                            w3DCode.setCodes(new Integer[]{e1, e2, k});
                            w3DCode.setSumTail((e1 + e2 + k) % 10);
                            w3DCodes.add(w3DCode);
                        }
                    }
                }
            }
        }

        // 去重 & 排序 & 转组选
        welfareCode.setW3DCodes(w3DCodes);
        welfareCode.distinct().toGroup().cleanFreq().sort(WelfareCode::bitSort).generate();

        return welfareCode;
    }

    public static List<W3DCode> permutation(W3DCode w3DCode){
        if(w3DCode == null){
            return Collections.emptyList();
        }

        List<W3DCode> w3DCodes = new ArrayList<>();
        w3DCodes.add(w3DCode);
        w3DCodes.add(new W3DCode(w3DCode.getCodes()[2], w3DCode.getCodes()[0], w3DCode.getCodes()[1]));
        w3DCodes.add(new W3DCode(w3DCode.getCodes()[0], w3DCode.getCodes()[2], w3DCode.getCodes()[1]));
        w3DCodes.add(new W3DCode(w3DCode.getCodes()[0], w3DCode.getCodes()[1], w3DCode.getCodes()[2]));
        w3DCodes.add(new W3DCode(w3DCode.getCodes()[1], w3DCode.getCodes()[2], w3DCode.getCodes()[0]));
        w3DCodes.add(new W3DCode(w3DCode.getCodes()[1], w3DCode.getCodes()[0], w3DCode.getCodes()[2]));

        return w3DCodes;
    }


}
