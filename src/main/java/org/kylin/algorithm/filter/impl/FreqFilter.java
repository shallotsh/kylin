package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.util.TransferUtil;
import org.kylin.util.WyfCollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/8/13 下午10:27.
 */
@Service
public class FreqFilter implements CodeFilter<WelfareCode> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreqFilter.class);

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null || CollectionUtils.isEmpty(code.getW3DCodes()) || filterParam == null || StringUtils.isBlank(filterParam.getFreqs())){
            return;
        }

        String freqString = filterParam.getFreqs();
        Set<Integer> freqs = TransferUtil.toIntegerSet(freqString);

        if(CollectionUtils.isEmpty(freqs)){
            LOGGER.warn("freq-filter-sequence-2-set-empty freqString={}", freqString);
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();
        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();
            if(freqs.contains(w3DCode.getFreq())){
                iterator.remove();
            }
        }

        code.setW3DCodes(w3DCodes);
    }

}
