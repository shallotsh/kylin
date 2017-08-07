package org.kylin.util;

import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.W3DCode;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author huangyawu
 * @date 2017/7/2 下午4:18.
 */
public class TransferUtil {

    public static List<Set<Integer>> parse(String seq){
        if(StringUtils.isBlank(seq) || seq.length() < 2){
            return Collections.emptyList();
        }

        List<Set<Integer>> gossips = new ArrayList<>();
        String[] gosArr = seq.split("#|$|@|,|/| ");
        for(String gos : gosArr){
            Set<Integer> set = toIntegerSet(gos);

            if(!CollectionUtils.isEmpty(set)){
                gossips.add(set);
            }
        }

        return gossips;
    }

    public static Set<Integer> toIntegerSet(String seq){
        if(StringUtils.isBlank(seq)){
            return Collections.emptySet();
        }

        Set<Integer> set = new HashSet<>();

        for(char ch: seq.toCharArray()){
            if(ch >= '0' && ch <= '9'){
                set.add(ch - '0');
            }
        }

        return set;
    }

    public static List<Set<Integer>> toIntegerSets(List<String> seqs){
        if(CollectionUtils.isEmpty(seqs)){
            return Collections.emptyList();
        }

        List<Set<Integer>> seqInts = new ArrayList<>();
        seqs.forEach(seq -> {
            if(seq == null){
                return;
            }
            Set<Integer> set = toIntegerSet(seq);
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

    public static int findInGroupW3DCodesWithFreq(List<W3DCode> w3DCodes, W3DCode w3DCode){
        if(CollectionUtils.isEmpty(w3DCodes) || w3DCode == null){
            return -1;
        }

        for(int i=0; i<w3DCodes.size(); i++){
            W3DCode code = w3DCodes.get(i);
            if(max(code) == max(w3DCode) && min(code) == min(w3DCode)
                    && code.getSumTail() == w3DCode.getSumTail() && code.getFreq() == w3DCode.getFreq()){
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
        if(w3DCode == null || w3DCode.getCodes()[2] == null){
            return null;
        }

        return Math.max(Math.max(w3DCode.getCodes()[2], w3DCode.getCodes()[1]), w3DCode.getCodes()[0]);
    }


    public static Integer min(W3DCode w3DCode){
        if(w3DCode == null || w3DCode.getCodes()[2] == null){
            return null;
        }

        return Math.min(Math.min(w3DCode.getCodes()[2], w3DCode.getCodes()[1]), w3DCode.getCodes()[0]);
    }

    public static boolean isPairCode(W3DCode w3DCode){
        if(w3DCode == null){
            return false;
        }

        return w3DCode.getCodes()[0] == w3DCode.getCodes()[1]
                || w3DCode.getCodes()[2] == w3DCode.getCodes()[1]
                || w3DCode.getCodes()[1] == w3DCode.getCodes()[0];
    }

    public static List<W3DCode> getPairCodes(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<W3DCode> ret = new ArrayList<>();
        w3DCodes.forEach(w3DCode -> {
            if(isPairCode(w3DCode)){
                ret.add(w3DCode);
            }
        });

        return ret;
    }

    public static List<W3DCode> getNonPairCodes(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<W3DCode> ret = new ArrayList<>();
        w3DCodes.forEach(w3DCode -> {
            if(!isPairCode(w3DCode)){
                ret.add(w3DCode);
            }
        });

        return ret;
    }
}
