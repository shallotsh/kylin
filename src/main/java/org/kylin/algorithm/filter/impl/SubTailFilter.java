package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.util.TransferUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 和值尾杀码
 * @author huangyawu
 * @date 2017/7/23 下午12:37.
 */
public class SubTailFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode welfareCode, FilterParam filterParam) {
        if(welfareCode == null ||
                CollectionUtils.isEmpty(welfareCode.getW3DCodes()) ||
                filterParam == null ||
                StringUtils.isBlank(filterParam.getSumValue())){
            return;
        }

        Set<Integer> st = TransferUtil.toIntegerSet(filterParam.getSumValue());
        List<W3DCode> w3DCodes = welfareCode.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(!st.contains(w3DCode.getSumTail())){
                iterator.remove();
            }
        }

        welfareCode.setW3DCodes(w3DCodes);
    }

}
