package org.kylin.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeSummarise;
import org.kylin.constant.BitConstant;

import java.util.*;
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
        Collections.sort(permutationFiveCodes);
        return permutationFiveCodes;
    }


    public static List<WCode> filterLowSumCodes(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes) || !validateCodes(wCodes)){
            return Collections.emptyList();
        }

        List<WCode> ret = wCodes.stream().filter(wCode -> wCode.sum() > 10).collect(Collectors.toList());

        return ret;

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


    public static boolean isInHistoryotteryAtLeastOneBit(WCode wCode, List<Integer> list){
        if(wCode == null || CollectionUtils.isEmpty(wCode.getCodes()) || CollectionUtils.isEmpty(list)){
            return false;
        }

        int size = CollectionUtils.size(wCode.getCodes()) > list.size() ? list.size(): CollectionUtils.size(wCode.getCodes());

        for(int i=0; i<size; i++){
            if(wCode.getCodes().get(i) == list.get(i)){
                return true;
            }
        }

        return false;
    }

    public static boolean isInFishCodeWithoutOrder(WCode wCode, List<Set<Integer>> fishManList){
        if(CollectionUtils.isEmpty(fishManList) || wCode == null || CollectionUtils.size(wCode.getCodes()) < 2){
            return true;
        }

        int dim = wCode.getDim();
        for (Set<Integer> fishMain : fishManList){
            if(fishMain.contains(wCode.getCodes().get(dim -1)) &&
                    fishMain.contains(wCode.getCodes().get(dim - 2))){
                return true;
            }
        }

        return false;
    }

    public static boolean isInFishCodeWithOrder(WCode wCode, List<List<Integer>> fishManList){
        if(CollectionUtils.isEmpty(fishManList) || wCode == null || CollectionUtils.size(wCode.getCodes()) < 2){
            return true;
        }

        for (List<Integer> fishMain : fishManList){
            if(!fishMain.contains(wCode.getCodes().get(0))
                    || !fishMain.contains(wCode.getCodes().get(1))){
                continue;
            }

            int p1 = fishMain.indexOf(wCode.getCodes().get(0));
            int p2 = fishMain.indexOf(wCode.getCodes().get(1));

            if(p1 != -1 && p1 <= p2){
                return true;
            }
        }

        return false;
    }


    public static boolean compareTailThreeBit(List<W3DCode> w3DCodes, WCode wCode){
        if(CollectionUtils.isEmpty(w3DCodes) || wCode == null){
            return true;
        }

        if(CollectionUtils.size(wCode.getCodes()) < 3){
            return false;
        }

        int dim = wCode.getDim();

        for (W3DCode w3DCode : w3DCodes){
            Integer[] codes = w3DCode.getCodes();
            if(codes.length < 3 || codes[BitConstant.HUNDRED] == null){
                continue;
            }

            if(codes[BitConstant.UNIT] == wCode.getCodes().get(dim - 1)
                    && codes[BitConstant.DECADE] == wCode.getCodes().get(dim - 2)
                    && codes[BitConstant.HUNDRED] == wCode.getCodes().get(dim - 3)){
                return true;
            }

        }

        return false;

    }

    public static boolean isExtremumCodes(WCode wCode){
        if(wCode == null ){
            return false;
        }

        List<Integer> codes = wCode.getCodes().stream().filter(e -> e > 5 || e == 0).collect(Collectors.toList());
        if(CollectionUtils.size(codes) == 5 || CollectionUtils.size(codes) == 0){
            return true;
        }

        return false;
    }

    public static boolean isPair(WCode wCode){
        if(wCode == null){
            return false;
        }

        return wCode.getCodes().get(1) == wCode.getCodes().get(2)
                || wCode.getCodes().get(1) == wCode.getCodes().get(0)
                || wCode.getCodes().get(0) == wCode.getCodes().get(2);
    }

    public static List<WCode> filterPairCodes(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes)){
            return Collections.emptyList();
        }

        return wCodes.stream().filter(wCode -> isPair(wCode)).collect(Collectors.toList());
    }

    public static List<WCode> filterNonPairCodes(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes)){
            return Collections.emptyList();
        }

        return wCodes.stream().filter(wCode -> !isPair(wCode)).collect(Collectors.toList());
    }

    public static Integer getPairCodeCount(List<WCode> wCodes){
        return CollectionUtils.size(filterPairCodes(wCodes));
    }

    public static Integer getNonPairCodeCount(List<WCode> wCodes){
        return CollectionUtils.size(filterNonPairCodes(wCodes));
    }

    public static Integer getPairCodeCountRemained(List<WCode> wCodes){
        List<WCode> pairCodes = filterPairCodes(wCodes);
        if(CollectionUtils.isEmpty(pairCodes)){
            return 0;
        }
        List<WCode> remainedCodes = pairCodes.stream().filter(wCode -> !wCode.isDeleted()).collect(Collectors.toList());
        return CollectionUtils.size(remainedCodes);
    }

    public static Integer getNonPairCodeCountRemained(List<WCode> wCodes){
        List<WCode> nonPairCodes = filterNonPairCodes(wCodes);
        if(CollectionUtils.isEmpty(nonPairCodes)){
            return 0;
        }

        List<WCode> remainedCodes = nonPairCodes.stream().filter(wCode -> !wCode.isDeleted()).collect(Collectors.toList());
        return CollectionUtils.size(remainedCodes);
    }


    public static WCodeSummarise construct(List<WCode> wCodes, Boolean isRandomKill){
        WCodeSummarise wCodeSummarise =  new WCodeSummarise()
                .setwCodes(wCodes);
        if(isRandomKill != null && isRandomKill){
            wCodeSummarise.setPairCodes(WCodeUtils.getPairCodeCountRemained(wCodes))
                    .setNonPairCodes(WCodeUtils.getNonPairCodeCountRemained(wCodes))
                    .setRemainedCodesCount(WCodeUtils.getRemainedCodes(wCodes))
                    .setRandomKill(isRandomKill);
        }else {
            wCodeSummarise.setPairCodes(WCodeUtils.getPairCodeCount(wCodes))
                    .setNonPairCodes(WCodeUtils.getNonPairCodeCount(wCodes));
        }

        return wCodeSummarise;
    }



    public static<T> List<T> getFirstNRowsAndLastRowsInEveryPage(List<T> codes, Integer colNumInPage, Integer rowNumInPage, Integer count){
        if(CollectionUtils.isEmpty(codes) || count < 1){
            return Collections.emptyList();
        }

        if(count > codes.size()){
            return codes;
        }

        List<List<T>> codesArray = Lists.partition(codes, colNumInPage);
        List<List<List<T>>> codesPage = Lists.partition(codesArray, rowNumInPage);

        List<T> ret = new ArrayList<>();
        for(List<List<T>> codeArray: codesPage){
            if(CollectionUtils.size(codeArray) < count * 2 && CollectionUtils.size(codeArray) > 0){
                codeArray.forEach(list -> ret.addAll(ret));
                continue;
            }

            for(int i=0; i<count; i++){
                ret.addAll(codeArray.get(i));
                ret.addAll(codeArray.get(codeArray.size()-1 - i));
            }
        }

        return ret;
    }


    public static void plusFreq(List<WCode> baseWCodes){
        if(CollectionUtils.isEmpty(baseWCodes)){
            return;
        }

        for(WCode baseWCode : baseWCodes){
            if(!baseWCode.isDeleted()){
                baseWCode.increaseFreq();
            }
        }
    }

    public static int getHighestFreq(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes)){
            return 0;
        }

        int freq = 0;
        for(WCode wCode: wCodes){
            if(wCode.getFreq() > freq){
                freq = wCode.getFreq();
            }
        }

        return freq;

    }


    public static int getRemainedCodes(List<WCode> wCodes){
        if(CollectionUtils.isEmpty(wCodes)){
            return 0;
        }

        List<WCode> remainedCodes = wCodes.stream().filter(wCode -> !wCode.isDeleted()).collect(Collectors.toList());

        return CollectionUtils.size(remainedCodes);
    }

}
