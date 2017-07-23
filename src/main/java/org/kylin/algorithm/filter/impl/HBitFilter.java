package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.util.TransferUtil;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 百位杀码
 * @author huangyawu
 * @date 2017/7/23 下午3:21.
 */
@Service
public class HBitFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                StringUtils.isBlank(filterParam.gethBits())){
            return;
        }

        Set<Integer> hBits = TransferUtil.toIntegerSet(filterParam.gethBits());

        if(CollectionUtils.isEmpty(hBits)){
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(hBits.contains(w3DCode.getCodes()[2])){
                continue;
            }
            iterator.remove();
        }

        code.setW3DCodes(w3DCodes);

    }
}
