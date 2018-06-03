package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.constant.BitConstant;
import org.kylin.util.TransferUtil;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 有序钓叟
 * @author huangyawu
 * @date 2018/6/3 下午3:19.
 */
@Service
public class OrderFishManFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                StringUtils.isBlank(filterParam.getOrderFishMan())){
            return;
        }

        List<List<Integer>> orderFishManList = TransferUtil.parseLists(filterParam.getOrderFishMan());

        if(CollectionUtils.isEmpty(orderFishManList)){
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();

            boolean flag = false;

            for (List<Integer> fishMain : orderFishManList){
                if(CollectionUtils.isEmpty(fishMain)){
                    continue;
                }

                int index1 = fishMain.indexOf(w3DCode.getCodes()[BitConstant.HUNDRED]);
                int index2 = fishMain.indexOf(w3DCode.getCodes()[BitConstant.DECADE]);
                if(index1 != -1 && index2 >= index1){
                    flag = true;
                    break;
                }

            }
            if(!flag) {
                iterator.remove();
            }
        }

        code.setW3DCodes(w3DCodes);
    }
}
