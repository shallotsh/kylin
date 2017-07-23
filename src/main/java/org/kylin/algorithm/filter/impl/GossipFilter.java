package org.kylin.algorithm.filter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.CodeFilter;
import org.kylin.bean.FilterParam;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.util.TransferUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 八卦二码杀码法
 * @author huangyawu
 * @date 2017/7/23 下午3:13.
 */
@Service
public class GossipFilter implements CodeFilter<WelfareCode>{

    @Override
    public void filter(WelfareCode code, FilterParam filterParam) {
        if(code == null ||
                CollectionUtils.isEmpty(code.getW3DCodes()) ||
                filterParam == null ||
                StringUtils.isBlank(filterParam.getGossip())){
            return;
        }

        List<Set<Integer>> gossips = TransferUtil.parse(filterParam.getGossip());

        if(CollectionUtils.isEmpty(gossips)){
            return;
        }

        List<W3DCode> w3DCodes = code.getW3DCodes();

        Iterator<W3DCode> iterator = w3DCodes.iterator();
        while(iterator.hasNext()){
            W3DCode w3DCode = iterator.next();

            boolean flag = false;
            for (Set<Integer> gossip : gossips){
                if(CollectionUtils.size(gossip) != 2){
                    continue;
                }

                Set<Integer> tmp = new HashSet<>();

                int count = 0;
                if(gossip.contains(w3DCode.getCodes()[0]) && !tmp.contains(w3DCode.getCodes()[0])){
                    tmp.add(w3DCode.getCodes()[0]);
                    count++;
                }

                if(gossip.contains(w3DCode.getCodes()[1]) && !tmp.contains(w3DCode.getCodes()[1])){
                    tmp.add(w3DCode.getCodes()[1]);
                    count++;
                }

                if(gossip.contains(w3DCode.getCodes()[2]) && !tmp.contains(w3DCode.getCodes()[2])){
                    tmp.add(w3DCode.getCodes()[2]);
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
