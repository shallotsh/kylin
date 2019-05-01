package org.kylin.service.xcode.filters.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.p5.WCode;
import org.kylin.service.xcode.filters.SimpleFilter;
import org.kylin.util.TransferUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 跨度杀码, 见 RangeFilter
 *
 */
public class KdSimpleFilter implements SimpleFilter{

    @Override
    public List<WCode> filter(List<WCode> target, String filterStr) {

        if(CollectionUtils.isEmpty(target)
                || StringUtils.isBlank(filterStr)){
            return target;
        }

        Set<Integer> rangeSet = TransferUtil.toIntegerSet(filterStr);

        if(CollectionUtils.isEmpty(rangeSet)){
            return target;
        }

        return target.stream()
                .filter(wCode -> isInRange(wCode, rangeSet))
                .collect(Collectors.toList());
    }


    private boolean isInRange(WCode wCode, Set<Integer> rangeSet){

        Objects.requireNonNull(wCode);
        Objects.requireNonNull(rangeSet);

        return rangeSet.contains(Math.abs(wCode.getCodes().get(0)));

    }
}
