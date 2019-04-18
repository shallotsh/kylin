package org.kylin.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.bean.p5.WCodeSummarise;
import org.kylin.constant.BitConstant;
import org.kylin.constant.FilterStrategyEnum;
import org.kylin.constant.WelfareConfig;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class WCodeUtils {


    public static WCode mock(int dim){
        WCode wCode = new WCode();
        if(dim <= 0) return wCode;

        List<Integer> codes = new ArrayList<>();
        for(int i=0; i<dim; i++){
            codes.add(Math.floorMod((int)(Math.random()*10), 10));
        }
        wCode.setCodes(codes);
        log.info("mock wcode: {}", wCode);
        return wCode;
    }


    public static List<WCode> mockCodes(int dim, int count){
        if(count <= 0 || dim <= 0){
            return Collections.emptyList();
        }

        List<WCode> wCodes = new ArrayList<>();
        for(int i=0; i<count; i++){
            wCodes.add(mock(dim));
        }

        return wCodes;
    }

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


    public static WCodeSummarise construct(List<WCode> wCodes, String key, List<WCode> deletedCodes, WCodeReq wCodeReq){
        WCodeSummarise wCodeSummarise =  new WCodeSummarise()
                .setwCodes(wCodes);

        if(Objects.isNull(wCodeReq)){
            return wCodeSummarise;
        }

        // process deleted codes
        Map<String, List<WCode>> delMaps = wCodeReq.getDeletedCodesPair();
        if(delMaps == null){
            delMaps = new HashMap<>();
        }

        if(StringUtils.isNotEmpty(key) && !CollectionUtils.isEmpty(deletedCodes)){
            List<WCode> codes = delMaps.getOrDefault(key, new ArrayList<>());
            codes.addAll(deletedCodes);
            delMaps.put(key, codes);
        }

        wCodeSummarise.setDeletedCodesPair(delMaps);

        // process random kill
        if(wCodeReq != null) {
            Boolean isRandomKill = wCodeReq.getFilterType() != null && wCodeReq.getFilterType() == FilterStrategyEnum.RANDOM_FILTER.getId();

            if (isRandomKill != null && isRandomKill) {
                wCodeSummarise.setPairCodes(WCodeUtils.getPairCodeCountRemained(wCodes))
                        .setNonPairCodes(WCodeUtils.getNonPairCodeCountRemained(wCodes))
                        .setRemainedCodesCount(WCodeUtils.getRemainedCodes(wCodes))
                        .setRandomKill(isRandomKill);
            } else {
                wCodeSummarise.setPairCodes(WCodeUtils.getPairCodeCount(wCodes))
                        .setNonPairCodes(WCodeUtils.getNonPairCodeCount(wCodes));
            }

            Boolean isFreqSeted = wCodeReq.getFilterType() != null &&
                    (FilterStrategyEnum.BOLD_INCREASE_FREQ.getId().equals(wCodeReq.getFilterType())
                            || FilterStrategyEnum.SUM_INCREASE_FREQ.getId().equals(wCodeReq.getFilterType()));
            wCodeSummarise.setFreqSeted(isFreqSeted);
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


    /**
     *  P5 前三位与胆码比较，若有一位包含在胆码中，则返回true
     * @param wCode             P5码
     * @param boldCodes         胆码
     * @return
     */
    public static boolean containsBoldCode(WCode wCode, Set<Integer> boldCodes){

        if(wCode == null || CollectionUtils.size(wCode.getCodes()) < 3){
            return false;
        }

        Set<Integer> codes = new HashSet<>(wCode.getCodes().subList(0,3));

        Set<Integer> intersection = Sets.intersection(codes, boldCodes);
        
        return !CollectionUtils.isEmpty(intersection);
    }


    public static boolean containsInHighFreqCodes(WCode wCode){
        if(wCode == null || CollectionUtils.size(wCode.getCodes()) < 3){
            return false;
        }

        String code = "" + wCode.getCodes().get(0) + wCode.getCodes().get(1) + wCode.getCodes().get(2);

        if(WelfareConfig.hfcSets.contains(code)){
            return true;
        }

        return false;
    }

    public static boolean isEqual(WCode w1, WCode w2){
        if(w1 == w2) return true;
        if(w1 == null || w2 == null) return false;
        if(w1.getDim() != w2.getDim()) return false;
        for(int i=0; i<w1.getDim(); i++){
            if(!Objects.equals(w1.getCodes().get(i), w2.getCodes().get(i))){
                return false;
            }
        }

        return true;
    }

    public static List<WCode> merge(List<List<WCode>> wCodesArray){
        if(CollectionUtils.isEmpty(wCodesArray)){
            return Collections.emptyList();
        }

        List<WCode> retCodes = wCodesArray.get(0);
        for(int i=1; i<wCodesArray.size(); i++){
            List<WCode> wCodes =  wCodesArray.get(i);
            for(WCode wCode : wCodes){
                boolean tag = false;
                for(WCode ret : retCodes){
                    if(ret.equals(wCode)) {
                        ret.setFreq(ret.getFreq() + wCode.getFreq());
                        tag = true;
                        break;
                    }
                }
                if(!tag) {
                    retCodes.add(wCode);
                }
            }
        }

        return retCodes;
    }


}
