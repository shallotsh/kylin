package org.kylin.factory;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.algorithm.strategy.impl.*;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.constant.FilterStrategyEnum;

import java.util.List;

public class StrategyFactory {
    public static SequenceProcessor createProcessor(FilterStrategyEnum filterStrategyEnum){
        switch (filterStrategyEnum){
            case LITTLE_SUM_FILTER:
                return new LitttleSumProcessor();
            case BIG_SUM_FILTER:
                return new BigSumProcessor();
            case ODD_EVEN_FILTER:
                return new OddEvenProcessor();
            case CONTAIN_THREE_FILTER:
                return new ContainThreeProcessor();
            case CONTAIN_FOUR_FILTER:
                return new ContainFourProcessor();
            case CONTAIN_FIVE_FILTER:
                return new ContainFiveProcessor();
            case EXTREMUM_FILTER:
                return new ExtremumProcessor();
            case FISH_MAN_FILTER:
                return new FishManProcessor();
                default:
                    return null;
        }
    }


    public static Strategy< List<WCode>,  WCodeReq> constructBitFilterStrategy(){
        return new BitFilterStrategy();
    }
}
