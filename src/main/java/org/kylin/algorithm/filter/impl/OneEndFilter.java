package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;

import java.util.Iterator;
import java.util.List;

/**
 * 全大全小杀码法
 * @author huangyawu
 * @date 2017/7/23 下午3:33.
 */
public class OneEndFilter implements CodeFilter<WelfareCode> {

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                filterParam.getKillOneEnd() == null ||
                filterParam.getKillOneEnd() == false){
            return;
        }

        List<W3DCode> w3DCodeList = code.getW3DCodes();
        Iterator<W3DCode> iterator = w3DCodeList.iterator();

        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(isOneEnd(w3DCode)){
                iterator.remove();
            }
        }

        code.setW3DCodes(w3DCodeList);
    }


    private boolean isOneEnd(W3DCode w3DCode) {
        int high = 0;
        int low = 0;
        for (int i = 0; i < 3; i++) {
            if(w3DCode.getCodes()[i] == null){
                continue;
            }
            if (w3DCode.getCodes()[i] == 0 || w3DCode.getCodes()[i] > 5) {
                high++;
            } else {
                low++;
            }
        }
        if ((high == 3 || low == 3) && (w3DCode.getCodes()[2] == null && (high == 2 || low == 2))) {
            return true;
        } else {
            return false;
        }
    }
}
