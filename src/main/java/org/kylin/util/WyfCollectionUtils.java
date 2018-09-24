package org.kylin.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.RandomKill;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.bean.p5.WCode;
import org.kylin.constant.CodeTypeEnum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huangyawu
 * @date 2017/7/16 下午3:56.
 */
public class WyfCollectionUtils {
    public static List<W3DCode> minus(List<W3DCode> w3DCodes, List<W3DCode> codes, CodeTypeEnum codeTypeEnum){
        if(CollectionUtils.isEmpty(codes) || CollectionUtils.isEmpty(w3DCodes) || codeTypeEnum == null){
            return w3DCodes;
        }

        List<W3DCode> ret = new ArrayList<>();

        if(CodeTypeEnum.DIRECT.equals(codeTypeEnum)) {
            codes.forEach(w3DCode -> {
                int index = TransferUtil.findInDirectW3DCodes(w3DCodes, w3DCode);
                if ( index < 0){
                    ret.add(w3DCode);
                }
            });
        }else if(CodeTypeEnum.GROUP.equals(codeTypeEnum)){
            codes.forEach(w3DCode -> {
                int index = TransferUtil.findInGroupW3DCodes(w3DCodes, w3DCode);
                if ( index < 0){
                    ret.add(w3DCode);
                }
            });
        }

        return ret;
    }

    public static List<W3DCode> union(List<W3DCode> w3DCodes, List<W3DCode> codes, CodeTypeEnum codeTypeEnum){
        if(CollectionUtils.isEmpty(codes) || CollectionUtils.isEmpty(w3DCodes) || codeTypeEnum == null){
            return w3DCodes;
        }

        List<W3DCode> ret = new ArrayList<>();

        if(CodeTypeEnum.DIRECT.equals(codeTypeEnum)) {
            codes.forEach(w3DCode -> {
                int index = TransferUtil.findInDirectW3DCodes(w3DCodes, w3DCode);
                if ( index >= 0){
                    ret.add(w3DCode);
                }
            });
        }else if(CodeTypeEnum.GROUP.equals(codeTypeEnum)){
            codes.forEach(w3DCode -> {
                int index = TransferUtil.findInGroupW3DCodes(w3DCodes, w3DCode);
                if ( index >= 0){
                    ret.add(w3DCode);
                }
            });
        }

        return ret;
    }


    public static<T> List<T> getSubList(List<T> wCodes, int pageSize, int startPosInPage){
        if(CollectionUtils.isEmpty(wCodes)){
            return Collections.emptyList();
        }

        List<T> ret = new ArrayList<>();
        List<List<T>> codesArray = Lists.partition(wCodes, pageSize);

        for(List<T> codes: codesArray){
            if(CollectionUtils.isEmpty(codes)){
                continue;
            }

            if(CollectionUtils.size(codes) < pageSize){
                ret.addAll(codes.subList(codes.size()/2,codes.size()));
            }else{
                ret.addAll(codes.subList(startPosInPage, codes.size()));
            }
        }

        return ret;
    }

    public static<T> List<T> getRandomList(List<T> wCodes, Integer count){
        if(CollectionUtils.isEmpty(wCodes) || CollectionUtils.size(wCodes) < count){
            return wCodes;
        }

        List<T> ret = new ArrayList<>();
        Set<Integer> isSelected = new HashSet<>();
        Integer size = wCodes.size();

        for(int i=0; i<count && i<size; i++){
            int index = new Random().nextInt(size);
            if(isSelected.contains(i)){
                continue;
            }
            ret.add(wCodes.get(index));
            isSelected.add(i);
        }

        return ret;
    }

    public static<T> List<List<T>> getRandomLists(List<T> wCodes, int randomCount, int randomSize){
        if(randomCount < 1){
            return Collections.emptyList();
        }
        if(CollectionUtils.isEmpty(wCodes) || randomSize > wCodes.size()){
            return Arrays.asList(wCodes);
        }

        List<List<T>> ret = new ArrayList<>();

        for(int i=0; i< randomCount; i++){
            List<T> randomList = getRandomList(wCodes, randomSize);
            if(!CollectionUtils.isEmpty(randomList)){
                ret.add(randomList);
            }
        }

        return ret;
    }

    public static<T extends RandomKill> void markRandomDeletedByCount(List<T> wCodes, int randomCount){

        int count = 0;
        while(randomCount > 0 && CollectionUtils.size(wCodes) > count){
            int randomSize = wCodes.size();
            int index = new Random().nextInt(randomSize);
            if(wCodes.get(index).isBeDeleted()){
                count ++;
                continue;
            }
            wCodes.get(index).setBeDeleted(true);
            randomCount -= 1;
        }
    }


    public static List<List<W3DCode>> splitByFreq(List<W3DCode> w3DCodes) {
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        int count = 0;
        int freq = 0;
        List<List<W3DCode>> w3DCodeList = new ArrayList<>();
        while (count < w3DCodes.size()){
            List<W3DCode> tmp = new ArrayList<>();

            for(W3DCode w3DCode: w3DCodes){
                if(w3DCode.getFreq() == freq){
                    tmp.add(w3DCode);
                }
            }
            if(CollectionUtils.isNotEmpty(tmp)){
                w3DCodeList.add(tmp);
            }
            freq ++;
            count += CollectionUtils.size(tmp);
        }

        return w3DCodeList;
    }

    public static boolean compareTwoW3DCode(W3DCode w1, W3DCode w2){
        if(w1 == w2){
            return true;
        }
        if(w1 == null || w2 == null){
            return false;
        }

        if(w1.getCodes().length != w2.getCodes().length){
            return false;
        }

        boolean[] flag = new boolean[w2.getCodes().length];
        for(int i=0; i<flag.length; i++){
            flag[i] = false;
        }
        int count = 0;

        for(int c1: w1.getCodes()){
            for(int i=0; i<w2.getCodes().length; i++){
                if(c1 == w2.getCodes()[i] && !flag[i]){
                    flag[i] = true;
                    count++;
                }
            }
        }

        return count == flag.length;
    }


    public static List<W3DCode> orderingWithGroup(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<List<W3DCode>> w3DCodeArray = new ArrayList<>();

        for(W3DCode w3DCode: w3DCodes){
            int index = -1;
            for(int i=0; i<w3DCodeArray.size(); i++){
                List<W3DCode> w3DCodeList = w3DCodeArray.get(i);
                if(CollectionUtils.isNotEmpty(w3DCodeList) && compareTwoW3DCode(w3DCodeList.get(0), w3DCode)){
                    w3DCodeList.add(w3DCode);
                    index = i;
                    break;
                }
            }
            if(index == -1){
                List<W3DCode> w3DCodeList = new ArrayList<>();
                w3DCodeList.add(w3DCode);
                w3DCodeArray.add(w3DCodeList);
            }
        }

        List<W3DCode> ret = new ArrayList<>();
        for(List<W3DCode> list : w3DCodeArray){
            ret.addAll(list);
        }

        return ret;
    }

}
