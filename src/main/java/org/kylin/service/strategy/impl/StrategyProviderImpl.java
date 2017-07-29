package org.kylin.service.strategy.impl;

import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.WelfareCode;
import org.kylin.bean.WyfParam;
import org.kylin.service.strategy.StrategyProvider;
import org.springframework.stereotype.Service;

/**
 * @author huangyawu
 * @date 2017/7/29 下午2:09.
 */
@Service
public class StrategyProviderImpl implements StrategyProvider{

    @Override
    public WelfareCode encode(WyfParam param, Strategy<? super WelfareCode, ? super WyfParam> strategy) {
        return (WelfareCode) strategy.execute(param);
    }
}
