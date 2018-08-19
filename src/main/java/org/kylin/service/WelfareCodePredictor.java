package org.kylin.service;

import org.kylin.bean.FilterParam;
import org.kylin.bean.P3Param;
import org.kylin.bean.PolyParam;
import org.kylin.bean.WelfareCode;
import org.kylin.constant.CodeTypeEnum;

import java.util.List;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:35.
 */
public interface WelfareCodePredictor {
    /**
     * 预测编码
     * @param riddles
     * @param codeTypeEnum
     * @return
     */
    WelfareCode encode(List<String> riddles, CodeTypeEnum codeTypeEnum);

    /**
     * 杀码过滤器
     * @param filterParam
     * @return
     */
    WelfareCode filter(FilterParam filterParam);


    /**
     * 取余，差码
     * @param polyParam
     * @return
     */
    WelfareCode minus(PolyParam polyParam);


    /**
     * 综合选码
     * @param welfareCodes
     * @return
     */
    WelfareCode compSelect(List<WelfareCode> welfareCodes);

    /**
     * 高频杀码
     * @param welfareCode
     * @return
     */
    WelfareCode highFreq(WelfareCode welfareCode);


    /**
     *  和值尾增频一
     *
     * @param p3Param
     * @return
     */
    WelfareCode increaseFreqBySumTail(P3Param p3Param);
}
