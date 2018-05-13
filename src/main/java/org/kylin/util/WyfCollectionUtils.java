package org.kylin.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.p5.WCode;
import org.kylin.constant.CodeTypeEnum;

import java.util.*;

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
}
