package org.kylin.service.xcode.filters.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.kylin.bean.p5.WCode;
import org.kylin.service.xcode.filters.SimpleFilter;
import org.kylin.util.TransferUtil;
import org.kylin.util.WCodeUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BoldCodeFilter implements SimpleFilter{

    @Override
    public List<WCode> filter(List<WCode> target, String filterStr) {

        if(CollectionUtils.isEmpty(target)){
            return Collections.emptyList();
        }

        Set<Integer> boldSet = TransferUtil.toIntegerSet(filterStr);

        if(CollectionUtils.isEmpty(boldSet)){
            return target;
        }

        List<WCode> ret = target.stream().filter(
                wCode -> WCodeUtils.containInSet(wCode, boldSet) > 0
        ).collect(Collectors.toList());

        return ret;
    }
}
