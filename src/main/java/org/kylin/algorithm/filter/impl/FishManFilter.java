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
 * 钓叟杀码
 * @author huangyawu
 * @date 2017/7/23 下午3:19.
 */
@Service
public class FishManFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                StringUtils.isBlank(filterParam.getFishMan())){
            return;
        }

        List<Set<Integer>> fishManList = TransferUtil.parse(filterParam.getFishMan());

        if(CollectionUtils.isEmpty(fishManList)){
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();

            boolean flag = false;
            for (Set<Integer> fishMain : fishManList){

                int count = 0;
                if(fishMain.contains(w3DCode.getCodes()[0])){
                    count++;
                }

                if(fishMain.contains(w3DCode.getCodes()[1])){
                    count++;
                }

                if(fishMain.contains(w3DCode.getCodes()[2])){
                    count++;
                }

                if(count >= 2){
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
