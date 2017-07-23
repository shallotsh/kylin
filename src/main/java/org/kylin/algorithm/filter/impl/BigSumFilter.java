package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;

import java.util.Iterator;
import java.util.List;

/**
 * 杀大和
 * @author huangyawu
 * @date 2017/7/23 下午3:29.
 */
public class BigSumFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                filterParam.getKillBigSum() == null ||
                filterParam.getKillBigSum() == false){
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(w3DCode.sum() > 20) {
                iterator.remove();
            }
        }

        code.setW3DCodes(w3DCodes);


    }
}
