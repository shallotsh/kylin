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
 * 胆杀
 * @author huangyawu
 * @date 2017/7/23 下午3:12.
 */
public class BoldFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                StringUtils.isBlank(filterParam.getBoldCode())){
            return;
        }

        Set<Integer> boldSet = TransferUtil.toIntegerSet(filterParam.getBoldCode());

        if(CollectionUtils.isEmpty(boldSet)){
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(boldSet.contains(w3DCode.getCodes()[0])||
                    boldSet.contains(w3DCode.getCodes()[1]) ||
                    boldSet.contains(w3DCode.getCodes()[2])){
                continue;
            }
            iterator.remove();
        }

        code.setW3DCodes(w3DCodes);
    }

}
