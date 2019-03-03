package org.kylin.util;

import org.kylin.algorithm.pattern.BitSeqEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BitsUtils {

    public static List<BitSeqEnum> getBitSeqEnums(List<Integer> bitSeqIds){
        List<BitSeqEnum> bitSeqEnums = new ArrayList<>();

        for(int id: bitSeqIds){
            if(id <= 0) continue;
            Optional<BitSeqEnum> seqEnumOpt = BitSeqEnum.getById(id);
            if(seqEnumOpt.isPresent()){
                bitSeqEnums.add(seqEnumOpt.get());
            }
        }
        return bitSeqEnums;
    }
}
