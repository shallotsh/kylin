package org.kylin.util;

import org.kylin.bean.W3DCode;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author huangyawu
 * @date 2017/7/2 下午4:18.
 */
public class TransferUtil {
    public static List<Set<Integer>> toIntegerSet(List<String> seqs){
        if(CollectionUtils.isEmpty(seqs)){
            return Collections.emptyList();
        }

        List<Set<Integer>> seqInts = new ArrayList<>();
        seqs.forEach(seq -> {
            if(seq == null){
                return;
            }
            Set<Integer> set = new HashSet<>();
            for(char ch: seq.toCharArray()){
                if(ch >= '0' && ch <= '9'){
                    set.add(ch - '0');
                }
            }
            if(!CollectionUtils.isEmpty(set)){
                seqInts.add(set);
            }
        });

        return seqInts;
    }

    public static int findInDirectW3DCodes(List<W3DCode> w3DCodes, W3DCode w3DCode){
        if(CollectionUtils.isEmpty(w3DCodes) || w3DCode == null){
            return -1;
        }

        for(int i=0; i<w3DCodes.size(); i++){
            if(w3DCode.equals(w3DCodes.get(i))){
                return i;
            }
        }

        return -1;
    }


    public static int findInGroupW3DCodes(List<W3DCode> w3DCodes, W3DCode w3DCode){
        if(CollectionUtils.isEmpty(w3DCodes) || w3DCode == null){
            return -1;
        }

        for(int i=0; i<w3DCodes.size(); i++){
            W3DCode code = w3DCodes.get(i);
            if(max(code) == max(w3DCode) && min(code) == min(w3DCode)
                    && code.getSumTail() == w3DCode.getSumTail()){
                return i;
            }
        }

        return -1;
    }

    public static Integer max(W3DCode w3DCode){
        if(w3DCode == null || w3DCode.getH() == null){
            return null;
        }

        return Math.max(Math.max(w3DCode.getH(), w3DCode.getD()), w3DCode.getU());
    }


    public static Integer min(W3DCode w3DCode){
        if(w3DCode == null || w3DCode.getH() == null){
            return null;
        }

        return Math.min(Math.min(w3DCode.getH(), w3DCode.getD()), w3DCode.getU());
    }
}
