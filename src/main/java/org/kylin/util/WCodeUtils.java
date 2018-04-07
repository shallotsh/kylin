package org.kylin.util;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.p5.WCode;
import org.kylin.constant.BitConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WCodeUtils {

    public static boolean validateCodes(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes)){
            return true;
        }

        List<WCode> wCodes1 = wCodes.stream().filter(wCode -> !wCode.validate()).collect(Collectors.toList());
        return CollectionUtils.isEmpty(wCodes1);
    }

    public static WCode fromW3DCode(W3DCode w3DCode){
        if(w3DCode == null){
            return null;
        }

        WCode wCode;
        if(w3DCode.getCodes()[BitConstant.HUNDRED] == null){
            wCode = new WCode(2, w3DCode.getCodes()[BitConstant.DECADE],w3DCode.getCodes()[BitConstant.UNIT]);
        }else{
            wCode = new WCode(3, w3DCode.getCodes()[BitConstant.HUNDRED], w3DCode.getCodes()[BitConstant.DECADE],w3DCode.getCodes()[BitConstant.UNIT]);
        }
        return wCode;
    }

    public static List<WCode> fromW3DCodes(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<WCode> wCodes = new ArrayList<>();
        w3DCodes.forEach(w3DCode -> {
            WCode wCode = fromW3DCode(w3DCode);
            if(w3DCode != null){
                wCodes.add(wCode);
            }
        });

        return wCodes;
    }


    public static List<WCode> transferToPermutationFiveCodes(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes) || !validateCodes(wCodes) || wCodes.get(0).getDim() != 3){
            return Collections.emptyList();
        }

        List<WCode> permutationFiveCodes = new ArrayList<>();
        for(WCode wCode: wCodes){
            for(int i=0; i<100; i++){
                int lastFirst = i%10;
                int lastSecond = (int)(i/10);
                if(lastFirst == lastSecond){
                    continue;
                }
                WCode pCode = new WCode(5, wCode.getCodes().get(BitConstant.HUNDRED),
                    wCode.getCodes().get(BitConstant.DECADE), wCode.getCodes().get(BitConstant.UNIT), lastSecond, lastFirst);
                permutationFiveCodes.add(pCode);
            }
        }

        return permutationFiveCodes;
    }

    public static boolean isAllEvenOrOdd(WCode wCode){
        if(wCode == null || CollectionUtils.isEmpty(wCode.getCodes())){
            return false;
        }

        List<Integer> odds = wCode.getCodes().stream().filter(e -> e % 2 == 0).collect(Collectors.toList());
        return CollectionUtils.size(odds) == 0 || CollectionUtils.size(odds) == wCode.getCodes().size();
    }

    public static int containInSet(WCode wCode, Set<Integer> set){
        if(wCode == null || CollectionUtils.isEmpty(wCode.getCodes()) || CollectionUtils.isEmpty(set)){
            return 0;
        }

        List<Integer> codes = wCode.getCodes().stream().filter(e -> set.contains(e)).collect(Collectors.toList());
        return CollectionUtils.size(codes);
    }

}
