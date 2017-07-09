package org.kylin.service.encode;

import org.kylin.bean.WelfareCode;

import java.util.List;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/6/29 下午11:26.
 */
public interface WyfEncodeService {
    /**
     * 二码编码器
     * @param riddles
     * @return
     */
    WelfareCode gossipEncoder(List<Set<Integer>> riddles);

    /**
     * 组选编码器
     * @param riddles
     * @return
     */
    WelfareCode groupSelectEncoder(List<Set<Integer>> riddles);

    /**
     * 直选编码器
     * @param riddles
     * @return
     */
    WelfareCode directSelectEncoder(List<Set<Integer>> riddles);


    /**
     * 组选转直选编码
     * @param groupWelfareCode
     * @return
     */
    WelfareCode group2directSelectEncoder(WelfareCode groupWelfareCode);

    /**
     * 直选转组选
     * @param directWelfareCode
     * @return
     */
    WelfareCode direct2GroupSelectEncoder(WelfareCode directWelfareCode);
}
