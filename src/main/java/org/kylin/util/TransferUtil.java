package org.kylin.util;

import javafx.util.Pair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.constant.CodeTypeEnum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huangyawu
 * @date 2017/7/2 下午4:18.
 */
public class TransferUtil {

    public static Set<Integer> parseSet(String seq){
        if(StringUtils.isBlank(seq)){
            return Collections.emptySet();
        }

        String[] strArray = seq.split("#|$|@|,|/| ");
        Set<Integer> sets = new HashSet<>();
        for(String e : strArray){
            if(StringUtils.isNumeric(e)){
                Integer sum = NumberUtils.toInt(e, 0);
                if("0".equals(e) || sum != 0){
                    sets.add(sum);
                }
            }
        }

        return sets;
    }

    public static List<Set<Integer>> parseGossipList(String seq){
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

    public static List<Pair<Integer,Integer>> parse2DGossipList(String seq){
        if(StringUtils.isBlank(seq) || seq.length() < 2){
            return Collections.emptyList();
        }

        List<Pair<Integer,Integer>> gossips = new ArrayList<>();
        String[] gosArr = seq.split("#|$|@|,|/| ");
        for(String gos : gosArr){
            List<Integer> list = toIntegerList(gos);

            if(!CollectionUtils.isEmpty(list) && list.size() == 2){
                gossips.add(new Pair<>(list.get(0), list.get(1)));
            }
        }

        return gossips;
    }


    public static List<List<Integer>> parseLists(String seq){
        if(StringUtils.isBlank(seq) || seq.length() < 2){
            return Collections.emptyList();
        }

        List<List<Integer>> ret = new ArrayList<>();
        String[] gosArr = seq.split("#|$|@|,|/| ");
        for(String gos : gosArr){
            List<Integer> list = toIntegerList(gos);

            if(!CollectionUtils.isEmpty(list)){
                ret.add(list);
            }
        }

        return ret;
    }


    public static List<Integer> toIntegerList(String seq){
        if(StringUtils.isBlank(seq)){
            return Collections.emptyList();
        }

        List<Integer> list = new ArrayList<>();

        for(char ch: seq.toCharArray()){
            if(ch >= '0' && ch <= '9'){
                list.add(ch - '0');
            }
        }

        return list;
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

    public static int findInW3DCodes(List<W3DCode> w3DCodes, W3DCode w3DCode, CodeTypeEnum codeType){
        if(codeType == null){
            throw new RuntimeException("参数错误");
        }

        if(codeType == CodeTypeEnum.DIRECT){
            return findInDirectW3DCodes(w3DCodes, w3DCode);
        }else if(codeType == CodeTypeEnum.GROUP){
            return findInGroupW3DCodes(w3DCodes, w3DCode);
        }else {
            throw new UnsupportedOperationException("暂不支持的操作");
        }
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

    /**
     * 组选编码中找出频度相等的3D码索引
     * @param w3DCodes
     * @param w3DCode
     * @return
     */
    public static int findInGroupW3DCodeWithFreq(List<W3DCode> w3DCodes, W3DCode w3DCode){
        List<Integer> ret = findGroupW3DCodeWithFreq(w3DCodes, w3DCode, w3DCode.getFreq());
        if(CollectionUtils.isEmpty(ret)){
            return -1;
        }else{
            return  ret.get(0);
        }
    }

    /**
     * 找出指定频度下与给定3D码为同一组选的索引列表
     * @param w3DCodes
     * @param w3DCode
     * @param freq
     * @return
     */
    private static List<Integer> findGroupW3DCodeWithFreq(List<W3DCode> w3DCodes, W3DCode w3DCode, int freq){
        if(CollectionUtils.isEmpty(w3DCodes) || w3DCode == null){
            return Collections.emptyList();
        }

        List<Integer> ret = new ArrayList<>();

        for(int i=0; i<w3DCodes.size(); i++){
            W3DCode code = w3DCodes.get(i);
            if(max(code) == max(w3DCode) && min(code) == min(w3DCode)
                    && code.getSumTail() == w3DCode.getSumTail() && code.getFreq() == freq){
                ret.add(i);
            }
        }

        return ret;
    }


    /**
     * 在直选编码中，找出指定频度下与给定3D码为同一组选的3D码列表
     * @param w3DCodes
     * @param w3DCode
     * @param freq
     * @return
     */
    public static List<W3DCode> findAllRepeat3DCodesWithFreq(List<W3DCode> w3DCodes, W3DCode w3DCode, int freq){
        if(CollectionUtils.isEmpty(w3DCodes) || w3DCode == null || freq < 0){
            return Collections.emptyList();
        }

        List<Integer> rets = findGroupW3DCodeWithFreq(w3DCodes, w3DCode, freq);

        if(CollectionUtils.isEmpty(rets)){
            return Collections.emptyList();
        }else{
            List<W3DCode> ret = new ArrayList<>();
            rets.forEach(index -> ret.add(w3DCodes.get(index)));
            return ret;
        }
    }

    public static List<W3DCode> grouplize(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }
        List<W3DCode> ret = new ArrayList<>();
        w3DCodes.forEach(w3DCode -> {
            int index = TransferUtil.findInGroupW3DCodes(ret, w3DCode);
            if(index < 0){
                ret.add(w3DCode);
            }
        });

        return ret;
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
                || w3DCode.getCodes()[2] == w3DCode.getCodes()[0];
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


    public static List<W3DCode> getNonDeletedPairCodes(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<W3DCode> ret = new ArrayList<>();
        w3DCodes.forEach(w3DCode -> {
            if(isPairCode(w3DCode) && !w3DCode.isBeDeleted()){
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

    public static int getDeletedCodeCount(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return 0;
        }

        List<W3DCode> ret = w3DCodes.stream().filter(w3DCode -> w3DCode.isBeDeleted()).collect(Collectors.toList());

        return CollectionUtils.size(ret);
    }

    public static void plusOneFreqs(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return;
        }

        w3DCodes.forEach(w3DCode -> {
            if(!w3DCode.isBeDeleted()){
                w3DCode.addFreq(1);
            }
        });
    }

    /**
     * 特殊方法:频度3，频度2的两两组选比较，有重叠者留下
     * @param w3DCodes
     * @return
     */
    public static List<W3DCode> findRepeatW3DCodesInFreqs23(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<W3DCode> ret = new ArrayList<>();
        for(W3DCode w3DCode: w3DCodes){
            if(w3DCode.getFreq() != 3){
                continue;
            }

            int index = findInGroupW3DCodeWithFreq(ret, w3DCode);
            if(index >= 0){
                // 已经存在，直接加入到结果
                ret.add(w3DCode);
                continue;
            }

            List<W3DCode> searchResult = findAllRepeat3DCodesWithFreq(w3DCodes, w3DCode, 2);
            if(!CollectionUtils.isEmpty(searchResult)){
                ret.add(w3DCode);
                ret.addAll(searchResult);
            }

        }

        // 频度排序
        Collections.sort(ret, WelfareCode::freqSort);

        return ret;
    }

    public static int getHighestFreq(List<W3DCode> w3DCodes){
        if(CollectionUtils.isEmpty(w3DCodes)){
            return 0;
        }

        int freq = 0;
        for(W3DCode code: w3DCodes){
            if(code.getFreq() > freq){
                freq = code.getFreq();
            }
        }

        return freq;

    }

    /**
     * 特殊方法:将频度4，频度3，频度2的两两组选比较，有重叠者留下
     * @param w3DCodes
     * @return
     */
    public static List<W3DCode> findAllRepeatW3DCodes(List<W3DCode> w3DCodes){

        if(CollectionUtils.isEmpty(w3DCodes)){
            return Collections.emptyList();
        }

        List<W3DCode> freq2W3DCodes = w3DCodes.stream().filter(w3DCode -> w3DCode.getFreq()==2).collect(Collectors.toList());
        List<W3DCode> freq3W3DCodes = w3DCodes.stream().filter(w3DCode -> w3DCode.getFreq()==3).collect(Collectors.toList());
        List<W3DCode> freq4W3DCodes = w3DCodes.stream().filter(w3DCode -> w3DCode.getFreq()==4).collect(Collectors.toList());

        Set<W3DCode> w3DCodeSet = new HashSet<>();

        w3DCodeSet.addAll(getSpecialIntersection(freq2W3DCodes, freq3W3DCodes, 3));
        w3DCodeSet.addAll(getSpecialIntersection(freq2W3DCodes, freq4W3DCodes, 4));
        w3DCodeSet.addAll(getSpecialIntersection(freq3W3DCodes, freq4W3DCodes, 4));

        return new ArrayList<>(w3DCodeSet);
    }


    public static List<W3DCode> getSpecialIntersection(List<W3DCode> codes1, List<W3DCode> codes2, int freq){
        if(CollectionUtils.isEmpty(codes1) || CollectionUtils.isEmpty(codes2)){
            return Collections.emptyList();
        }

        List<W3DCode> w3DCodes = new ArrayList<>();
        codes1.forEach(code -> {
            int index = findInGroupW3DCodeWithFreq(w3DCodes, code);
            if(index >= 0){
                // 已经存在，直接加入到结果
                w3DCodes.add(code);
                return;
            }
            List<W3DCode> searchResult = findAllRepeat3DCodesWithFreq(codes2, code, freq);
            if(!CollectionUtils.isEmpty(searchResult)){
                w3DCodes.add(code);
                w3DCodes.addAll(searchResult);
            }

        });

        return w3DCodes;
    }

    public static W3DCode parseFromString(String code){
        if(StringUtils.isEmpty(code) || code.length() != 3){
            return null;
        }
        W3DCode w3DCode = new W3DCode();
        int length = code.length();
        int total = 0;
        for(int i=0; i<length; i++){
            if(StringUtils.isNumeric(code.substring(i,i+1))){
                int tmp = Integer.parseInt(code.substring(i, i+1));
                w3DCode.getCodes()[length - i - 1] = tmp;
                total += tmp;
            }
        }
        w3DCode.setFreq(1000);
        w3DCode.setSumTail(total % 10);

        return w3DCode;
    }

    public static List<W3DCode> parseFromStringArrays(String[] codes){
        if(ArrayUtils.isEmpty(codes)){
            return Collections.emptyList();
        }

        List<W3DCode> w3DCodes = new ArrayList<>();
        for(String code: codes){
            W3DCode w3DCode = parseFromString(code);
            if(w3DCode != null)
            w3DCodes.add(w3DCode);
        }

        return w3DCodes;
    }
}
