package org.kylin.util;

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
}
