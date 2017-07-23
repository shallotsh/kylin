package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * 全大全小杀码法
 * @author huangyawu
 * @date 2017/7/23 下午3:34.
 */
@Service
public class AllOddEvenFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                filterParam.getKillAllOddEven() == null ||
                filterParam.getKillAllOddEven() == false){
            return;
        }


        List<W3DCode> w3DCodeList = code.getW3DCodes();
        Iterator<W3DCode> iterator = w3DCodeList.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(isAllEven(w3DCode) || isAllOdd(w3DCode)){
                iterator.remove();
            }
        }

        code.setW3DCodes(w3DCodeList);
    }

    private boolean isAllOdd(W3DCode w3DCode){
        boolean flag = ((w3DCode.getCodes()[0] % 2 != 0) && (w3DCode.getCodes()[1] % 2 != 0));
        if(flag && w3DCode.getCodes()[2] != null){
            return flag && (w3DCode.getCodes()[2] % 2 != 0);
        }
        return false;
    }

    private boolean isAllEven(W3DCode w3DCode){
        boolean flag = ((w3DCode.getCodes()[0] % 2 == 0) && (w3DCode.getCodes()[1] % 2 == 0));
        if(flag && w3DCode.getCodes()[2] != null){
            return flag && (w3DCode.getCodes()[2] % 2 == 0);
        }
        return false;
    }
}
